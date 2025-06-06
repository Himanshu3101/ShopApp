package com.example.shopapp.ui.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.shopapp.core.util.Constants.Shoplog
import com.example.shopapp.features.dashboard.presentation.screen.Dashboard
import com.example.shopapp.features.dashboard.presentation.screen.DashboardViewModel
import com.example.shopapp.features.introScreen.presentation.IntroScreen
import com.example.shopapp.features.productDetail.presentation.ProductDetailViewModel
import com.example.shopapp.features.productDetail.presentation.ProductDetailUI
import com.example.shopapp.features.productList.presentation.ProductTypeUI
import com.example.shopapp.features.productList.presentation.ProductTypeViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun SetNavGraph() {

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Routes.IntoScreen.route) {
        composable(route = Routes.IntoScreen.route) {
            IntroScreen(navController)
        }

        composable(route = Routes.Dashboard.route) {
            val viewModel = hiltViewModel<DashboardViewModel>()
            val state by viewModel.dashboardState.collectAsStateWithLifecycle()

            // --- NEW: Observe Navigation Events from ViewModel ---
            LaunchedEffect(navController, viewModel) {
                viewModel.navigaionEvent.collectLatest { navigationEvent ->
                    when(navigationEvent){
                        is DashboardViewModel.NavigationEvent.ToProductList->{
                            // CORRECT CALL: Access createRoute directly from Routes.ProductlistUI
                            navController.navigate(Routes.ProductTypeUI.createRoute(navigationEvent.categoryId, navigationEvent.title))
                        }

                        is DashboardViewModel.NavigationEvent.ProductDetails -> {
                            navController.navigate(Routes.ProductDetail.createRoute(navigationEvent.idItems))
                        }
                    }
                }
            }

            Dashboard(navController, event = viewModel::onEvent, state = state)


        }



        composable(
            route = Routes.ProductTypeUI.route,
            arguments = Routes.ProductTypeUI.navArgument // This automatically picks up the defined navArgument list
        ) {
            val productListViewModel = hiltViewModel<ProductTypeViewModel>()
            val productListState by productListViewModel.productListState.collectAsStateWithLifecycle()

            /*            LaunchedEffect (navController, productListViewModel){
                productListViewModel.navigationEvents.collectLatest { navigationEvent ->
                    when(navigationEvent){
                        is ProductListViewModel.NavigationEvent.ToProductDetail -> {
                            navController.navigate(Routes.ProductDetail.createRoute(navigationEvent.productId))
                        }
                    }
                }
            }*/

            ProductTypeUI(
                navController = navController,
                state = productListState,
                event = productListViewModel::onEvent,
            )
        }


        composable(
            route = Routes.ProductDetail.route,
            arguments = Routes.ProductDetail.navArgument
        ) { backStackEntry ->
            val productId = backStackEntry.arguments?.getString(Routes.ProductDetail.navArgument.first().name)

             val productDetailViewModel = hiltViewModel<ProductDetailViewModel>()
             val productDetailState by productDetailViewModel.productDetailState.collectAsStateWithLifecycle()
             ProductDetailUI(navController, state = productDetailState, event = productDetailViewModel::onEvent)

        }
    }


}




