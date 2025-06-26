package com.example.shopapp.featuresTest.dashboard.domainRepositoryTest

import com.example.shopapp.core.network.ShopApi
import com.example.shopapp.features.dashboard.data.remote.dto.ItemsResponse
import com.example.shopapp.features.dashboard.data.repository.ItemRepositoryImpl
import com.example.shopapp.features.dashboard.domain.remote.model.ItemDomain
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class ItemRepositoryTest {

    private lateinit var mockShopApi: ShopApi
    private lateinit var itemRepository: ItemRepositoryImpl

    @Before
    fun setUp() {
        mockShopApi = mock()
        itemRepository = ItemRepositoryImpl(mockShopApi)
    }

    @Test
    fun `getItems return a list of items on successfull API response`() = runTest {

        val expectedItems = listOf(
            ItemDomain(idItems = 1, categoryId = 0, createdAt = "Today", description = "ABC", objectId = "banner_id_1", updatedAt = "TodY",
                price = 1, rating = 2.0, showRecommended = true, title = "url_1",
                picUrl = listOf(
                    "https://res.cloudinary.com/dkikc5ywq/image/upload/v1746098536/1_1_db56nv.png",
                    "https://res.cloudinary.com/dkikc5ywq/image/upload/v1746098532/1_4_vdstgc.jpg"
                ),
                cartQuantity = 0,
            ),
            ItemDomain(idItems = 1, categoryId = 1, createdAt = "yESTERday", description = "DEF", objectId = "banner_id_2", updatedAt = "yESTerday",
                title = "url_2", price = 1, rating = 2.0, showRecommended = true,
                picUrl = listOf(
                    "https://res.cloudinary.com/dkikc5ywq/image/upload/v1746098536/1_1_db56nv.png",
                    "https://res.cloudinary.com/dkikc5ywq/image/upload/v1746098532/1_4_vdstgc.jpg"
                ),
                cartQuantity = 0,
            )
        )

        val apiResponse = ItemsResponse(expectedItems)

        whenever(mockShopApi.getItems()).thenReturn(apiResponse)

        val actualItems = itemRepository.getItems()

        assert(expectedItems.size == actualItems.size)
        assert(expectedItems == actualItems)
    }

    @Test
    fun `getItems return an EMPTY list when API response is empty`() = runTest{
        val emptyApiResponse = ItemsResponse(results = emptyList())

        whenever(mockShopApi.getItems()).thenReturn(emptyApiResponse)

        val actualItems = itemRepository.getItems()

        assertTrue(actualItems.isEmpty())
        assertEquals(0, actualItems.size)
    }

    @Test
    fun `getItems throws exception when API call fails`() = runTest {
        val expectedErrorMessage = "Network error occurred"

        whenever(mockShopApi.getItems()).thenAnswer { throw Exception (expectedErrorMessage) }

        val exception = assertFailsWith < Exception>(message = "Network error occurred") {
            itemRepository.getItems() // This block is expected to throw the exception
        }

        assertEquals(expectedErrorMessage, exception.message)
    }
}