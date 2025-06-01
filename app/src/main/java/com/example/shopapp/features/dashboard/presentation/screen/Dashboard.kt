package com.example.shopapp.features.dashboard.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.shopapp.R
import com.example.shopapp.ui.components.GridWith_Images_Details
import com.example.shopapp.features.dashboard.presentation.screen.component.Header_UserScrn
import com.example.shopapp.features.dashboard.presentation.screen.component.Scroller_ProductSlider
import com.example.shopapp.features.dashboard.presentation.screen.component.Search_UserScrn
import com.example.shopapp.features.dashboard.presentation.screen.event.DashboardUiEvent
import com.example.shopapp.features.dashboard.presentation.screen.state.DashboardUiState
import com.example.shopapp.ui.components.ImagePager
import com.example.shopapp.ui.theme.Dimens


@Preview(showBackground = true)
@Composable
fun PrevDash() {
    Dashboard(
        navHostController = rememberNavController(),
        event = {},
        state = DashboardUiState(),
    )
}

@Composable
fun Dashboard(
    navHostController: NavHostController,
    event: (DashboardUiEvent) -> Unit,
    state: DashboardUiState,
) {

    // Trigger InitDashboard only once when the Composable is first launched.
    // The ViewModel's `isInitialized` flag will prevent redundant API calls
    LaunchedEffect(Unit) {  // Use Unit as key to ensure it runs only once
            event(DashboardUiEvent.InitDashboard)
    }

    // Display loading indicator if data is being fetched
    if (state.isLoading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                // Add this modifier to make it testable
                modifier = Modifier.testTag("LoadingIndicator")
            )
        }
        return // Don't render content until loading is complete or an error occurs
    }

    //Display error message if present
    if(state.errorMessage != null){
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(Dimens.SmallPadding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Text(
                text = "Error: ${state.errorMessage}",
                color = MaterialTheme.colorScheme.error,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyLarge
            )
            Spacer(modifier = Modifier.height(Dimens.SmallSpacerHeight))
            Text(
                text = "Tap to retry",
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.clickable { event(DashboardUiEvent.InitDashboard) }, // Retry on click
                style = MaterialTheme.typography.bodyMedium
            )
        }
        return // Don't render content if there's an error displayed
    }




    // Render content only if not loading and no error
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(
                top = Dimens.ExtraLargePadding,
                start = Dimens.SmallPadding,
                end = Dimens.SmallPadding
            )
            .testTag(stringResource(R.string.dashboard_lazy_column)),
        verticalArrangement = Arrangement.spacedBy(Dimens.SmallSpacerHeight)
    ) {

        item {
            Header_UserScrn()
        }

        item {
            Search_UserScrn()
        }


        item {
            if (state.bannerUrls.isNotEmpty()) {
                Box(modifier = Modifier.testTag(stringResource(R.string.pagerSection))) {
                    ImagePager(imageUrls = state.bannerUrls)
                }
            } else if(!state.isLoading && state.isInitialized){
                Text(
                    text = stringResource(R.string.no_banners_available), // Define this string resource
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }


        // Product Category Section
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag(stringResource(R.string.product))
                    .padding(Dimens.SmallPadding),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = stringResource(R.string.product),
                    style = MaterialTheme.typography.titleMedium
                )

                Text(
                    text = stringResource(R.string.see_all),
                    onTextLayout = {}, // required parameter
                    modifier = Modifier.clickable { },
                    style = TextStyle(color = colorResource(id = R.color.blue)),
//                onClick = {}
                )

            }
        }

        // Only show Scroller_ProductSlider if categoryList is not empty
        item {
            if (state.categoryList.isNotEmpty()) {
                Box(modifier = Modifier.testTag(stringResource(R.string.product_categories_title)))
                Scroller_ProductSlider(productList = state.categoryList, event = event)
            }else if (!state.isLoading && state.isInitialized){
                Text(
                    text = stringResource(R.string.no_categories_available), // Define this string resource
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

        }

//        Popular Products
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag(stringResource(R.string.popular_product),)
                    .padding(Dimens.SmallPadding),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = stringResource(R.string.popular_product),
                    style = MaterialTheme.typography.titleMedium
                )

                Text(
                    text = stringResource(R.string.see_all),
                    onTextLayout = {}, // required parameter
                    modifier = Modifier.clickable { },
                    style = TextStyle(color = colorResource(id = R.color.blue)),
//                onClick = {}
                )

            }
        }

//        Grid Image
        item {

            if (state.itemsState.isNotEmpty()) {
                Box(modifier = Modifier.testTag(stringResource(R.string.gridTest)))
                    GridWith_Images_Details(state.itemsState)
            } else if (!state.isLoading && state.isInitialized) {
                // Optionally, show a placeholder or a message if items are empty after loading
                Text(
                    text = stringResource(R.string.no_popular_products_available), // Define this string resource
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

        }
    }

}


