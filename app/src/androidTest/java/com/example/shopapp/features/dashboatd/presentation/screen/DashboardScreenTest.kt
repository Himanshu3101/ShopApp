package com.example.shopapp.features.dashboatd.presentation.screen

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.filterToOne
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToNode
import androidx.navigation.testing.TestNavHostController
import androidx.test.platform.app.InstrumentationRegistry
import com.example.shopapp.R
import com.example.shopapp.features.dashboard.domain.remote.model.BannerDomain
import com.example.shopapp.features.dashboard.presentation.screen.Dashboard
import com.example.shopapp.features.dashboard.presentation.screen.event.DashboardUiEvent
import com.example.shopapp.features.dashboard.presentation.screen.state.CategoryDetails
import com.example.shopapp.features.dashboard.presentation.screen.state.DashboardUiState
import com.example.shopapp.features.dashboard.presentation.screen.state.ItemData
import com.example.shopapp.ui.theme.ShopAppTheme
import org.junit.Rule
import org.junit.Test

class DashboardScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    // --- Helper Data ---
    private val Banners = listOf(
        BannerDomain(
            objectId = "banner_id_1",
            createdAt = "Today",
            updatedAt = "TodY",
            url = "https://picsum.photos/300/200?random=1"
        ),
        BannerDomain(
            objectId = "banner_id_2",
            createdAt = "yESTERday",
            updatedAt = "yESTerday",
            url = "https://picsum.photos/300/200?random=2"
        ),
    )
    private val Categories = listOf(
        CategoryDetails(
            title = "url_1",
            id = 1
        ),
        CategoryDetails(
            title = "url_2",
            id = 2
        )
    )
    private val Items = listOf(
        ItemData(
            categoryId = 0,
            price = 1,
            rating = 2.0,
            showRecommended = true,
            title = "url_1",
            imageUrl = listOf(
                "https://res.cloudinary.com/dkikc5ywq/image/upload/v1746098536/1_1_db56nv.png",
                "https://res.cloudinary.com/dkikc5ywq/image/upload/v1746098532/1_4_vdstgc.jpg"
            )
        ),
        ItemData(
            categoryId = 1,
            title = "url_2",
            price = 1,
            rating = 2.0,
            showRecommended = true,
            imageUrl = listOf(
                "https://res.cloudinary.com/dkikc5ywq/image/upload/v1746098536/1_1_db56nv.png",
                "https://res.cloudinary.com/dkikc5ywq/image/upload/v1746098532/1_4_vdstgc.jpg"
            )
        )
    )

    private fun getString(resId: Int): String {
        return InstrumentationRegistry.getInstrumentation().targetContext.getString(resId)
    }

    //    Initial Loading State - Helper to set content for the Dashboard Composable
    private fun setDashboardContent(
        state: DashboardUiState,
        event: (DashboardUiEvent) -> Unit = {}
    ) {
        composeTestRule.setContent {
            val navHostController = TestNavHostController(LocalContext.current)
            // Wrap with app's theme
            ShopAppTheme {
                Dashboard(
                    navHostController = navHostController,
                    event = event,
                    state = state
                )
            }
        }
    }

    @Test
    fun dashboard_displayedLoadingState_whenLoading() {
        // Arrange: Set the UI state to loading
        setDashboardContent(DashboardUiState(isLoading = true))
        // Assert: Verify that the CircularProgressIndicator is displayed using its testTag
        composeTestRule.onNodeWithTag("LoadingIndicator").assertIsDisplayed()
        // You can still keep these assertions to ensure other elements are not displayed
        composeTestRule.onNodeWithText("Error:", ignoreCase = true).assertIsNotDisplayed()
        composeTestRule.onNodeWithText("Product", ignoreCase = true).assertIsNotDisplayed()

    }

    //    Error State
    @Test
    fun dashboard_displayedErrorMessageAndRetry_onError() {
        var eventTriggered = false
        setDashboardContent(
            DashboardUiState(errorMessage = "Failed To Load Data"),
            event = {
                if (it is DashboardUiEvent.InitDashboard) {
                    eventTriggered = true
                }
            }
        )
        composeTestRule.onNodeWithText("Error: Failed To Load Data", ignoreCase = true)
            .assertIsDisplayed()
        composeTestRule.onNodeWithText("Tap to retry", ignoreCase = true).assertIsDisplayed()
            .performClick()
        assert(eventTriggered) { "InitDashboard event not triggered when retry is clicked" }


    }

    //    Content Displayed (Success State - Empty Banners/Categories/Items)
    @Test
    fun dashboard_displayedEmptyState_whenDataInitializedButEmpty() {
        setDashboardContent(
            DashboardUiState(
                isLoading = false,
                isInitialized = true,
                bannerUrls = emptyList(),
                categoryList = emptyList(),
                itemsState = emptyList()
            )
        )

        composeTestRule.onNodeWithTag("LoadingIndicator").assertDoesNotExist()
        composeTestRule.onNodeWithText(getString(R.string.no_banners_available)).assertIsDisplayed()
        composeTestRule.onNodeWithText(getString(R.string.product)).assertIsDisplayed()
        composeTestRule.onNodeWithTag(getString(R.string.product)).onChildren()
            .filterToOne(hasText(getString(R.string.see_all))).assertIsDisplayed()


        composeTestRule.onNodeWithText(getString(R.string.no_categories_available))
            .assertIsDisplayed()
        composeTestRule.onNodeWithText(getString(R.string.popular_product)).assertIsDisplayed()
        composeTestRule.onNodeWithTag(getString(R.string.popular_product)).onChildren()
            .filterToOne(hasText(getString(R.string.see_all))).assertIsDisplayed()


        composeTestRule.onNodeWithText(getString(R.string.no_popular_products_available))
            .assertIsDisplayed()
    }

    //        Content Displayed (Success State - With Data)
    @Test
    fun dashboard_displaysContent_whenDataIsAvailable() {
        setDashboardContent(
            DashboardUiState(
                isLoading = false,
                isInitialized = true,
                bannerUrls = Banners.map { it.url },
                categoryList = Categories,
                itemsState = Items
            )
        )

        composeTestRule.onNodeWithTag("LoadingIndicator").assertDoesNotExist()

        composeTestRule.onNodeWithTag(getString(R.string.dashboard_lazy_column))
            .performScrollToNode(hasTestTag(getString(R.string.pagerSection)))
        composeTestRule.onNode(hasTestTag("BannerImage_0")).isDisplayed()


        composeTestRule.onNodeWithText(getString(R.string.product)).assertIsDisplayed()
        composeTestRule.onNodeWithTag(getString(R.string.product)).onChildren()
            .filterToOne(hasText(getString(R.string.see_all))).assertIsDisplayed()

        composeTestRule.onNodeWithTag(getString(R.string.dashboard_lazy_column))
            .performScrollToNode(hasTestTag(getString(R.string.product_categories_title)))
        composeTestRule.onNode(hasTestTag(getString(R.string.slider))).isDisplayed()

        composeTestRule.onNodeWithText(getString(R.string.popular_product)).assertIsDisplayed()
        composeTestRule.onNodeWithTag(getString(R.string.popular_product)).onChildren()
            .filterToOne(hasText(getString(R.string.see_all))).assertIsDisplayed()

        composeTestRule.onNodeWithTag(getString(R.string.dashboard_lazy_column))
            .performScrollToNode(hasTestTag(getString(R.string.gridTest)))
        composeTestRule.onNode(hasTestTag(getString(R.string.gridImageTest))).isDisplayed()


    }
}
