package com.example.shopapp.features.productList.presentation

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.shopapp.R
import com.example.shopapp.core.util.Constants.Shoplog
import com.example.shopapp.features.productList.presentation.event.ProductListUiEvent
import com.example.shopapp.features.productList.presentation.state.ProductListState
import com.example.shopapp.ui.common.components.AppBar
import com.example.shopapp.ui.common.components.GridWith_Images_Details
import com.example.shopapp.ui.navigation.Routes

@Preview
@Composable
fun Prev_ProductListUI() {
    ProductTypeUI(
        navController = rememberNavController(),
        state = ProductListState(),
        event = {},
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductTypeUI(
    navController: NavHostController,
    state: ProductListState,
    event: (ProductListUiEvent) -> Unit
) {

    BackHandler {
        navController.navigate(Routes.Dashboard.route) {
            popUpTo(Routes.Dashboard.route) { inclusive = true }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize().background(colorResource(R.color.white))
    ) {



        Log.d(Shoplog, "Recomposing with state: $state")

        AppBar(title = state.title) {
            navController.navigate(Routes.Dashboard.route) {
                popUpTo(Routes.Dashboard.route) { inclusive = true }
            }
        }

        GridWith_Images_Details(state.items)
    }


}