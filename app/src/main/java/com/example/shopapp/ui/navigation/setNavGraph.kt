package com.example.shopapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.shopapp.features.dashboard.presentation.screen.Dashboard
import com.example.shopapp.features.dashboard.presentation.screen.DashboardViewModel
import com.example.shopapp.features.introScreen.presentation.IntroScreen
import com.example.shopapp.features.productList.ProductListUI
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
                            navController.navigate(Routes.ProductlistUI.createRoute(navigationEvent.categoryId))
                        }
                    }
                }
            }

            Dashboard(navController, event = viewModel::onEvent, state = state)


        }



        composable(
            route = Routes.ProductlistUI.route,
            arguments = Routes.ProductlistUI.navArgument // This automatically picks up the defined navArgument list
        ) {
//            val id =it.arguments?.getInt(CATEGORY_ID_ARG)
//            val title = it.arguments?.getString(selectedCategoryTitle)
            ProductListUI(
                navController = navController,
                state = productListState,
                event = productListViewModel::onEvent
            )
        }


        /*composable(
            route = Routes.ProductDetail.route,
            arguments = Routes.ProductDetail.navArguments
        ) { backStackEntry ->
            val productId = backStackEntry.arguments?.getString(Routes.ProductDetail.navArguments.first().name)
            // Here, you would instantiate ProductDetailViewModel and pass its state/event
            // val productDetailViewModel = hiltViewModel<ProductDetailViewModel>()
            // val productDetailState by productDetailViewModel.productDetailState.collectAsStateWithLifecycle()
            // ProductDetailUI(navController, state = productDetailState, event = productDetailViewModel::onEvent)
            Text("Product Details for ID: $productId") // Placeholder
        }*/
    }


}


