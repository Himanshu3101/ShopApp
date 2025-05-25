package com.example.shopapp.UseCasesTest

import com.example.shopapp.core.network.Resources
import com.example.shopapp.features.dashboard.domain.remote.model.Banner
import com.example.shopapp.features.dashboard.domain.remote.repositoy.BannerRepository
import com.example.shopapp.features.dashboard.domain.useCases.GetBanner_UC
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.doThrow
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi  // Needed for runTest and TestDispatcher
class GetBannerUCTest{

    @Mock
    private lateinit var mockBannerRepository: BannerRepository

    private lateinit var getbannerUc: GetBanner_UC

    // Use a TestDispatcher for controlling coroutine execution in tests
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup(){
        MockitoAnnotations.openMocks(this)   // Initialize mocks annotated with @Mock
        getbannerUc = GetBanner_UC(mockBannerRepository)     // Inject the mocked repository into the use case
        Dispatchers.setMain(testDispatcher)       // Set the test dispatcher as the main dispatcher for coroutines
    }

    @After
    fun tearDown(){
        Dispatchers.resetMain()     // Reset the main dispatcher after each test
    }

    @Test
    fun `invoke emits loading then success with banners when repository call is successful`() = runTest{
        //Arrange
        val expecetedBanners = listOf(
            Banner(objectId = "banner_id_1", createdAt = "Today", updatedAt = "TodY", url = "url_1"),
            Banner(objectId = "banner_id_2", createdAt = "yESTERday", updatedAt = "yESTerday", url = "url_2"),
        )
        whenever(mockBannerRepository.getBanners()).doReturn(expecetedBanners)

        val emissions = getbannerUc().toList()
        assert(emissions[0] is Resources.Loading)
        assert(emissions[1] is Resources.Success)
        assertEquals(expecetedBanners, (emissions[1] as Resources.Success).data)

    }

    @Test
    fun `invoke emits loading then Error when repository throws exception`() =  runTest {
        val errorMessage = "Network Error Occurred!"
        val exception = RuntimeException(errorMessage)
        whenever(mockBannerRepository.getBanners()).doThrow(exception)

        val emission = getbannerUc().toList()
        assert(emission[0] is Resources.Loading)
        assert(emission[1] is Resources.Error)
        assertEquals(errorMessage, (emission[1] as Resources.Error).message)

    }

    @Test
    fun `invoke emits loading then Error with generic message for null exception message`() = runTest{
        val exception = RuntimeException("Something Problem Occurred!")
        whenever(mockBannerRepository.getBanners()).doThrow(exception)

        val emission = getbannerUc().toList()

        assert(emission[0] is Resources.Loading)
        assert(emission[1] is Resources.Error)
        assertEquals("Something Problem Occurred!", (emission[1] as Resources.Error).message)
    }
}