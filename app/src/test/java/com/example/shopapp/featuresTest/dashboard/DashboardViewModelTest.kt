package com.example.shopapp.featuresTest.dashboard

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.example.shopapp.core.network.Resources
import com.example.shopapp.features.dashboard.domain.remote.model.BannerDomain
import com.example.shopapp.features.dashboard.domain.remote.model.CategoryDomain
import com.example.shopapp.features.dashboard.domain.remote.model.ItemDomain
import com.example.shopapp.features.dashboard.domain.useCases.GetBanner_UC
import com.example.shopapp.features.dashboard.domain.useCases.GetCategory_UC
import com.example.shopapp.features.dashboard.domain.useCases.GetItems_UC
import com.example.shopapp.features.dashboard.presentation.screen.DashboardViewModel
import com.example.shopapp.features.dashboard.presentation.screen.event.DashboardUiEvent
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import kotlin.test.DefaultAsserter.assertEquals
import kotlin.test.DefaultAsserter.assertNull
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue
import kotlin.time.Duration.Companion.milliseconds

@ExperimentalCoroutinesApi
class DashboardViewModelTest {

    // Rule to swap the background executor used by the Architecture Components with a synchronous one
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()


    //Initialize and making the mock
    private val mockGetBannerUc: GetBanner_UC = mockk()
    private val mockGetCategoryUc: GetCategory_UC = mockk()
    private val mockGetItemsUc: GetItems_UC = mockk()

    // ViewModel instance to be tested
    private lateinit var viewModel: DashboardViewModel

    @Before
    fun setUp() {
        // Set the TestDispatcher as the main dispatcher
        Dispatchers.setMain(testDispatcher)

        viewModel = DashboardViewModel(
            mockGetBannerUc,
            mockGetCategoryUc,
            mockGetItemsUc,
            testDispatcher // Inject the test dispatcher
        )

        // Configure mock behavior for use cases to return success
        coEvery { mockGetBannerUc.invoke() } returns flowOf(Resources.Success(mockBanners))
        coEvery { mockGetCategoryUc.invoke() } returns flowOf(Resources.Success(mockCategories))
        coEvery { mockGetItemsUc.invoke() } returns flowOf(Resources.Success(mockItems))


    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    // --- Helper Data ---
    private val mockBanners = listOf(
        BannerDomain(
            objectId = "banner_id_1",
            createdAt = "Today",
            updatedAt = "TodY",
            url = "url_1"
        ),
        BannerDomain(
            objectId = "banner_id_2",
            createdAt = "yESTERday",
            updatedAt = "yESTerday",
            url = "url_2"
        ),
    )
    private val mockCategories = listOf(
        CategoryDomain(
            objectId = "banner_id_1",
            createdAt = "Today",
            updatedAt = "TodY",
            title = "url_1",
            id = 1
        ),
        CategoryDomain(
            objectId = "banner_id_2",
            createdAt = "yESTERday",
            updatedAt = "yESTerday",
            title = "url_2",
            id = 2
        )
    )
    private val mockItems = listOf(
        ItemDomain(
            categoryId = 0,
            createdAt = "Today",
            description = "ABC",
            objectId = "banner_id_1",
            updatedAt = "TodY",
            price = 1,
            rating = 2.0,
            showRecommended = true,
            title = "url_1",
            picUrl = listOf(
                "https://res.cloudinary.com/dkikc5ywq/image/upload/v1746098536/1_1_db56nv.png",
                "https://res.cloudinary.com/dkikc5ywq/image/upload/v1746098532/1_4_vdstgc.jpg"
            )
        ),
        ItemDomain(
            categoryId = 1,
            createdAt = "yESTERday",
            description = "DEF",
            objectId = "banner_id_2",
            updatedAt = "yESTerday",
            title = "url_2",
            price = 1,
            rating = 2.0,
            showRecommended = true,
            picUrl = listOf(
                "https://res.cloudinary.com/dkikc5ywq/image/upload/v1746098536/1_1_db56nv.png",
                "https://res.cloudinary.com/dkikc5ywq/image/upload/v1746098532/1_4_vdstgc.jpg"
            )
        )
    )

    // --- Test Cases ---
    @Test
    fun `initial state is loading after init block processes`() = runTest {
        // No event triggered yet, just ViewModel initialization

        // ViewModel is initialized in @Before, which runs the init block.
        // However, the combine collector runs in viewModelScope, which uses testDispatcher.
        // We need to allow the dispatcher to run the initial combine emission.
        testDispatcher.scheduler.runCurrent()

        viewModel.dashboardState.test {
            val initial = awaitItem()
            assert(initial.isLoading)
            assert(!initial.isInitialized)
            assert(initial.itemsState.isEmpty())
            assert(initial.errorMessage == null)
            assert(initial.bannerUrls.isEmpty())
            assert(initial.categoryList.isEmpty())
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `initDashboard fetches all data successfully`() = runTest(testDispatcher) {
        // 2. Act: Trigger the data fetching
        viewModel.onEvent(DashboardUiEvent.InitDashboard)

        // Advance the dispatcher until all coroutines are idle (completed)
        advanceUntilIdle()

        // 3. Assert: Verify the final state of the DashboardUiState
        val finalState = viewModel.dashboardState.value

        // Assert that loading is false and there's no error
        assertFalse(finalState.isLoading)
        assertTrue(finalState.isInitialized)
        assertNull(finalState.errorMessage)

        // Assert that banner URLs are correctly mapped
        assertEquals(mockBanners.map { it.url }, finalState.bannerUrls)

        // Assert that category list is correctly mapped
        assertEquals(mockCategories.size, finalState.categoryList.size)
        assertEquals("url_1", finalState.categoryList[0].title)
        assertEquals("url_2", finalState.categoryList[1].title)

        // Assert that items state is correctly mapped
        assertEquals(mockItems.size, finalState.itemsState.size)
        assertEquals(true, finalState.itemsState[1].showRecommended)
    }

    @Test
    fun `initDashboard - does not re-fetch if already initialized successfully`() = runTest{
        viewModel.onEvent(DashboardUiEvent.InitDashboard)

        advanceUntilIdle()

        val finalState = viewModel.dashboardState.value

        assertFalse(finalState.isLoading)
        assertTrue(finalState.isInitialized)
        kotlin.test.assertNull(finalState.errorMessage)
        assertEquals(2, finalState.bannerUrls.size)

        // Arrange: Change mock responses for a *potential* re-fetch (these should NOT be called)
        val newMockBanner = listOf(
            BannerDomain(
                objectId = "banner_id_1N",
                createdAt = "TodayN",
                updatedAt = "TodYN",
                url = "url_1N"
            )
        )

        // This should not be invoked again
        coEvery { mockGetBannerUc.invoke() } returns flowOf(Resources.Success(newMockBanner))

        viewModel.onEvent(DashboardUiEvent.InitDashboard)
        advanceUntilIdle()

        // Assert: The state should remain the same as the initial successful fetch
        // and not reflect the 'newMockBanners'
        val finalStateAfterReFetch = viewModel.dashboardState.value

        assertFalse(finalStateAfterReFetch.isLoading)
        assertTrue(finalStateAfterReFetch.isInitialized)
        assertNull(finalStateAfterReFetch.errorMessage)
        assertEquals("url_1", finalStateAfterReFetch.bannerUrls[0]) // Should still be the old data

    }
}