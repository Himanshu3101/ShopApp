package com.example.shopapp.features.productList

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.shopapp.ui.components.AppBar
import com.example.shopapp.ui.components.GridWith_Images_Details
import com.example.shopapp.ui.navigation.Routes

@Preview
@Composable
fun Prev_ProductListUI() {
    ProductListUI(navController = rememberNavController())
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductListUI(
    navController: NavHostController,
  /*  state : ProductListUiState,
    event: (ProductListUiEvent) -> Unit*/
) {

    BackHandler {
        navController.navigate(Routes.Dashboard.route) {
            popUpTo(Routes.Dashboard.route) { inclusive = true }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        AppBar(title = "title") {
            navController.navigate(Routes.Dashboard.route) {
                popUpTo(Routes.Dashboard.route) { inclusive = true }
            }
        }

//        GridWith_Images_Details()
    }


}