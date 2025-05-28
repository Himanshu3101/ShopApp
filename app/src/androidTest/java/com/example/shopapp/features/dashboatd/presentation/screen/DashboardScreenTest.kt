package com.example.shopapp.features.dashboatd.presentation.screen

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.navigation.compose.rememberNavController
import com.example.shopapp.features.dashboard.presentation.screen.Dashboard
import com.example.shopapp.features.dashboard.presentation.screen.state.DashboardUiState
import org.junit.Rule
import org.junit.Test

class DashboardScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun dashboard_displayedLoadingState_whenLoading(){
        // Arrange: Set the UI state to loading
        val loadingState = DashboardUiState(isLoading = true)

        // Act: Set the content of the Composable to the Dashboard with loading state
        composeTestRule.setContent {
            Dashboard(
                navHostController = rememberNavController(),// NavController is not used in loading state
                event = {},// Event is not triggered in loading state
                state = loadingState
            )
        }

        // Assert: Verify that the CircularProgressIndicator is displayed using its testTag
        composeTestRule.onNodeWithTag("LoadingIndicator").assertIsDisplayed()

        // You can still keep these assertions to ensure other elements are not displayed
        composeTestRule.onNodeWithText("Error:", ignoreCase = true).assertIsNotDisplayed()
        composeTestRule.onNodeWithText("Product", ignoreCase = true).assertIsNotDisplayed()

    }
}