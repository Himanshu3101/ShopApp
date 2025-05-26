package com.example.shopapp.featuresTest.dashboard.UseCasesTest

import com.example.shopapp.core.network.Resources
import com.example.shopapp.features.dashboard.domain.remote.model.CategoryDomain
import com.example.shopapp.features.dashboard.domain.remote.repositoy.CategoryRepository
import com.example.shopapp.features.dashboard.domain.useCases.GetCategory_UC
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

@ExperimentalCoroutinesApi
class GetCategoryDomainUCTest {

    @Mock
    private lateinit var mockCategoryRepository: CategoryRepository
    private lateinit var getCategoryUC: GetCategory_UC

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp(){
        MockitoAnnotations.openMocks(this)
        getCategoryUC = GetCategory_UC(mockCategoryRepository)
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown(){
        Dispatchers.resetMain()
    }

    @Test
    fun `invoke emits loading then success with category when repository call is successful`() = runTest{
        //Arrange
        val expectedCategories = listOf(
            CategoryDomain(objectId = "banner_id_1", createdAt = "Today", updatedAt = "TodY", title = "url_1", id = 1),
            CategoryDomain(objectId = "banner_id_2", createdAt = "yESTERday", updatedAt = "yESTerday", title = "url_2", id = 2)
        )
        whenever(mockCategoryRepository.getCategory()).doReturn(expectedCategories)

        val emissions = getCategoryUC().toList()
        assert(emissions[0] is Resources.Loading)
        assert(emissions[1] is Resources.Success)
        assertEquals(expectedCategories, (emissions[1] as Resources.Success).data)

    }

    @Test
    fun `invoke emits loading then Error when repository throws exception`() =  runTest {
        val errorMessage = "Network Error Occurred!"
        val exception = RuntimeException(errorMessage)
        whenever(mockCategoryRepository.getCategory()).doThrow(exception)

        val emission = getCategoryUC().toList()
        assert(emission[0] is Resources.Loading)
        assert(emission[1] is Resources.Error)
        assertEquals(errorMessage, (emission[1] as Resources.Error).message)

    }

    @Test
    fun `invoke emits loading then Error with generic message for null exception message`() = runTest{
        val exception = RuntimeException("Something Problem Occurred!")
        whenever(mockCategoryRepository.getCategory()).doThrow(exception)

        val emission = getCategoryUC().toList()

        assert(emission[0] is Resources.Loading)
        assert(emission[1] is Resources.Error)
        assertEquals("Something Problem Occurred!", (emission[1] as Resources.Error).message)
    }
}