package com.example.shopapp.featuresTest.dashboard.domainRepositoryTest

import com.example.shopapp.core.network.ShopApi
import com.example.shopapp.features.dashboard.data.remote.dto.BannerResponse
import com.example.shopapp.features.dashboard.data.repository.BannerRepositoryImpl
import com.example.shopapp.features.dashboard.domain.remote.model.BannerDomain
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class BannerDomainRepositoryTest {

    // 1. Declare a mock for ShopApi
    private lateinit var mockShopApi: ShopApi

    // 2. The repository implementation we are testing
    private lateinit var bannerRepository: BannerRepositoryImpl


    @Before
    fun setup(){
        // Initialize the mock ShopApi before each test
        mockShopApi = mock()

        // Initialize the BannerRepositoryImpl with the mocked ShopApi
        bannerRepository = BannerRepositoryImpl(mockShopApi)
    }

    @Test
    fun `getBanners return a list of banners on successfull API response`() = runTest{
        // Given: Prepare the expected list of banners
        val expecetedBanners = listOf(
            BannerDomain(objectId = "banner_id_1", createdAt = "Today", updatedAt = "TodY", url = "url_1"),
            BannerDomain(objectId = "banner_id_2", createdAt = "yESTERday", updatedAt = "yESTerday", url = "url_2"),
        )

        val apiRespose = BannerResponse(results = expecetedBanners)

        // Given: Define the behavior of the mockShopApi when getBanners is called
        // When mockShopApi.getBanners() is invoked, return the apiResponse
        whenever(mockShopApi.getBanners()).thenReturn(apiRespose)

        // When: Call the method under test
        val actualBanners = bannerRepository.getBanners()

        // Then: Assert the result
        assertEquals(expecetedBanners.size, actualBanners.size)
        assertEquals(expecetedBanners, actualBanners) // Check if the lists are equal
    }

    @Test
    fun `getBanners return aA EMPTY list when API response is empty`() = runTest{
        val emptyApiResponse = BannerResponse(results = emptyList())

        whenever(mockShopApi.getBanners()).thenReturn(emptyApiResponse)

        val actualBanners = bannerRepository.getBanners()

        assertTrue(actualBanners.isEmpty())
        assertEquals(0, actualBanners.size)
    }

    @Test
    fun `getBanners throws exception when API call fails`() = runTest {
        val expectedErrorMessage = "Network error occurred"

        whenever(mockShopApi.getBanners()).thenAnswer { throw Exception (expectedErrorMessage) }

        val exception = assertFailsWith < Exception>(message = "Network error occurred") {
            bannerRepository.getBanners() // This block is expected to throw the exception
        }

        assertEquals(expectedErrorMessage, exception.message)
    }
}
