package com.example.shopapp.featuresTest.dashboard.UseCasesTest

import com.example.shopapp.core.network.Resources
import com.example.shopapp.features.dashboard.domain.remote.model.Items
import com.example.shopapp.features.dashboard.domain.remote.repositoy.ItemRepository
import com.example.shopapp.features.dashboard.domain.useCases.GetItems_UC
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
class GetItemUCTest {
    
    @Mock
    private lateinit var mockItemRepository: ItemRepository
    private lateinit var geItemUC: GetItems_UC
    
    private val testDispatcher = StandardTestDispatcher()
    
    @Before
    fun setUp(){
        MockitoAnnotations.openMocks(this)
        geItemUC = GetItems_UC(mockItemRepository)
        Dispatchers.setMain(testDispatcher)
    }
    
    @After
    fun tearDown(){
        Dispatchers.resetMain()
    }

    @Test
    fun `invoke emits loading then success with item when repository call is successful`() = runTest{
        //Arrange
        val expectedCategories = listOf(
            Items(categoryId = 0, createdAt = "Today", description = "ABC", objectId = "banner_id_1", updatedAt = "TodY",
                price = 1, rating = 2.0, showRecommended = true, title = "url_1",
                picUrl = listOf(
                "https://res.cloudinary.com/dkikc5ywq/image/upload/v1746098536/1_1_db56nv.png",
                "https://res.cloudinary.com/dkikc5ywq/image/upload/v1746098532/1_4_vdstgc.jpg"
                )
            ),
            Items(categoryId = 1, createdAt = "yESTERday", description = "DEF", objectId = "banner_id_2", updatedAt = "yESTerday",
                title = "url_2", price = 1, rating = 2.0, showRecommended = true,
                picUrl = listOf(
                    "https://res.cloudinary.com/dkikc5ywq/image/upload/v1746098536/1_1_db56nv.png",
                    "https://res.cloudinary.com/dkikc5ywq/image/upload/v1746098532/1_4_vdstgc.jpg"
                )
            )
        )
        whenever(mockItemRepository.getItems()).doReturn(expectedCategories)

        val emissions = geItemUC().toList()
        assert(emissions[0] is Resources.Loading)
        assert(emissions[1] is Resources.Success)
        assertEquals(expectedCategories, (emissions[1] as Resources.Success).data)

    }

    @Test
    fun `invoke emits loading then Error when repository throws exception`() =  runTest {
        val errorMessage = "Network Error Occurred!"
        val exception = RuntimeException(errorMessage)
        whenever(mockItemRepository.getItems()).doThrow(exception)

        val emission = geItemUC().toList()
        assert(emission[0] is Resources.Loading)
        assert(emission[1] is Resources.Error)
        assertEquals(errorMessage, (emission[1] as Resources.Error).message)

    }

    @Test
    fun `invoke emits loading then Error with generic message for null exception message`() = runTest{
        val exception = RuntimeException("Something Problem Occurred!")
        whenever(mockItemRepository.getItems()).doThrow(exception)

        val emission = geItemUC().toList()

        assert(emission[0] is Resources.Loading)
        assert(emission[1] is Resources.Error)
        assertEquals("Something Problem Occurred!", (emission[1] as Resources.Error).message)
    }
}