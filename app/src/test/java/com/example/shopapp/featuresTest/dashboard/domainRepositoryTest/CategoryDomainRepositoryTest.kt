package com.example.shopapp.featuresTest.dashboard.domainRepositoryTest

import com.example.shopapp.core.network.ShopApi
import com.example.shopapp.features.dashboard.data.remote.dto.CategoryResponse
import com.example.shopapp.features.dashboard.data.repository.CategoryRepositoryImpl
import com.example.shopapp.features.dashboard.domain.remote.model.CategoryDomain
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class CategoryDomainRepositoryTest {

    private lateinit var mockShopApi: ShopApi

    private lateinit var categoryRepository: CategoryRepositoryImpl

    @Before
    fun setUp(){
        mockShopApi = mock()
        categoryRepository = CategoryRepositoryImpl(mockShopApi)
    }

    @Test
    fun `getCategory return a list of category on successfull API response`() = runTest{

        val expectedCategories = listOf(
            CategoryDomain(objectId = "banner_id_1", createdAt = "Today", updatedAt = "TodY", title = "url_1", Pid = 1),
            CategoryDomain(objectId = "banner_id_2", createdAt = "yESTERday", updatedAt = "yESTerday", title = "url_2", Pid = 2)
        )

        val apiResponse = CategoryResponse(expectedCategories)

        whenever(mockShopApi.getCategory()).thenReturn(apiResponse)

        val actualCategories = categoryRepository.getCategory()

        assert(expectedCategories.size == actualCategories.size)
        assert(expectedCategories == actualCategories)
    }

    @Test
    fun `getCategory return an EMPTY list when API response is empty`() = runTest{
        val emptyApiResponse = CategoryResponse(results = emptyList())

        whenever(mockShopApi.getCategory()).thenReturn(emptyApiResponse)

        val actualCategory = categoryRepository.getCategory()

        assertTrue(actualCategory.isEmpty())
        assertEquals(0, actualCategory.size)
    }

    @Test
    fun `getCategory throws exception when API call fails`() = runTest {
        val expectedErrorMessage = "Network error occurred"

        whenever(mockShopApi.getCategory()).thenAnswer { throw Exception (expectedErrorMessage) }

        val exception = assertFailsWith < Exception>(message = "Network error occurred") {
            categoryRepository.getCategory() // This block is expected to throw the exception
        }

        assertEquals(expectedErrorMessage, exception.message)
    }

}