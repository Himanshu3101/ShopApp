package com.example.shopapp.featuresTest.dashboard

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
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import kotlin.time.Duration.Companion.milliseconds

@ExperimentalCoroutinesApi
class DashboardViewModelTest {

    private val testDispatcher = StandardTestDispatcher()


    //Initialize and making the mock
    private val mockGetBannerUc: GetBanner_UC = mockk()
    private val mockGetCategoryUc: GetCategory_UC = mockk()
    private val mockGetItemsUc: GetItems_UC = mockk()

    private lateinit var viewModel: DashboardViewModel

    @Before
    fun setUp(){
        // Set the TestDispatcher as the main dispatcher
        Dispatchers.setMain(testDispatcher)

        // Initialize ViewModel before each test
        viewModel = DashboardViewModel(mockGetBannerUc, mockGetCategoryUc, mockGetItemsUc)
    }

    @After
    fun tearDown(){
        Dispatchers.resetMain()
    }

    // --- Helper Data ---
    private val testBanner = listOf(
        BannerDomain(objectId = "banner_id_1", createdAt = "Today", updatedAt = "TodY", url = "url_1"),
        BannerDomain(objectId = "banner_id_2", createdAt = "yESTERday", updatedAt = "yESTerday", url = "url_2"),
    )
    private  val expectedCategories = listOf(
        CategoryDomain(objectId = "banner_id_1", createdAt = "Today", updatedAt = "TodY", title = "url_1", id = 1),
        CategoryDomain(objectId = "banner_id_2", createdAt = "yESTERday", updatedAt = "yESTerday", title = "url_2", id = 2)
    )
    private val expectedItems = listOf(
        ItemDomain(categoryId = 0, createdAt = "Today", description = "ABC", objectId = "banner_id_1", updatedAt = "TodY",
            price = 1, rating = 2.0, showRecommended = true, title = "url_1",
            picUrl = listOf(
                "https://res.cloudinary.com/dkikc5ywq/image/upload/v1746098536/1_1_db56nv.png",
                "https://res.cloudinary.com/dkikc5ywq/image/upload/v1746098532/1_4_vdstgc.jpg"
            )
        ),
        ItemDomain(categoryId = 1, createdAt = "yESTERday", description = "DEF", objectId = "banner_id_2", updatedAt = "yESTerday",
            title = "url_2", price = 1, rating = 2.0, showRecommended = true,
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
    fun `initDashboard fetches all data successfully`() = runTest (testDispatcher) {
        // Arrange: Mock use cases to return success
        coEvery { mockGetBannerUc() } returns flow {
            emit(Resources.Loading())
            delay(10.milliseconds) // Simulate network/data processing time
            emit(Resources.Success(testBanner))
        }
        coEvery { mockGetCategoryUc() } returns flow{
            emit(Resources.Loading())
            delay(10.milliseconds) // Simulate network/data processing time
            emit(Resources.Success(expectedCategories))
        }
        coEvery { mockGetItemsUc() } returns flow{
            emit(Resources.Loading())
            delay(10.milliseconds) // Simulate network/data processing time
            emit(Resources.Success(expectedItems))
        }

        // Ensure ViewModel's init block has run its first emission.
        // This makes sure the combine setup is active and has emitted initial Loading state.
        testDispatcher.scheduler.runCurrent()

        viewModel.dashboardState.test{
            // Initial loading state from ViewModel's init block
            val initialLoading = awaitItem()
            println("Test: State 1 (Initial): $initialLoading")
            assert(initialLoading.isLoading)
            assert(!initialLoading.isInitialized)
            assert(initialLoading.errorMessage == null) // Add this check to be sure

            // Trigger initDashboard event
            viewModel.onEvent(DashboardUiEvent.InitDashboard)

            // Allow the immediate ViewModel state update to process
            testDispatcher.scheduler.runCurrent()

            // The combine will re-emit loading state from individual flows after initDashboard
            // This is due to the `copy(isLoading = true)` and subsequent `Resources.Loading()` emissions
            val loadingAfterInit = awaitItem()
            println("Test: State 2 (After Trigger): $loadingAfterInit")
            assert(loadingAfterInit.isLoading)
            assert(!loadingAfterInit.isInitialized)
            assert(loadingAfterInit.errorMessage == null) // Check this too

            // Advance time to allow all flows to complete with success
//            testDispatcher.scheduler.advanceUntilIdle()
            testDispatcher.scheduler.advanceTimeBy(10.milliseconds)
            testDispatcher.scheduler.runCurrent()

            // Assert final successful state
            val successState = awaitItem()
            println("Test: State 3 (Success): $successState")
            assert(!successState.isLoading)
            assert(successState.isInitialized)
            assert(successState.errorMessage == null)
            assert(successState.bannerUrls == testBanner.map { it.url })
            assert(successState.categoryList.size == expectedCategories.size)
            assert(successState.itemsState.size == expectedItems.size)

            cancelAndConsumeRemainingEvents()
        }
    }
}