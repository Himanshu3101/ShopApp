package com.example.shopapp.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.shopapp.features.cart.presentation.CartScreen
import com.example.shopapp.features.cart.presentation.CartViewModel
import com.example.shopapp.features.dashboard.presentation.screen.Dashboard
import com.example.shopapp.features.dashboard.presentation.screen.DashboardViewModel
import com.example.shopapp.features.introScreen.presentation.IntroScreen
import com.example.shopapp.features.productDetail.presentation.ProductDetailViewModel
import com.example.shopapp.features.productDetail.presentation.ProductDetailUI
import com.example.shopapp.features.productList.presentation.ProductTypeUI
import com.example.shopapp.features.productList.presentation.ProductTypeViewModel
import com.example.shopapp.ui.components.AppBottomNavigationBar
import kotlinx.coroutines.flow.collectLatest

@Composable
fun SetNavGraph() {

    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            val currentRoute =
                navController.currentBackStackEntryAsState().value?.destination?.route
            if (currentRoute == Routes.Dashboard.route ||
                currentRoute == Routes.ProfileUI.route ||
                currentRoute == Routes.cartUI.route
            ) {
                AppBottomNavigationBar(navController = navController)
            }
        }
    ) { paddingValues ->


        NavHost(
            navController = navController,
            startDestination = Routes.IntoScreen.route,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(route = Routes.IntoScreen.route) {
                IntroScreen(navController)
            }

            composable(route = Routes.Dashboard.route) {
                val viewModel = hiltViewModel<DashboardViewModel>()
                val state by viewModel.dashboardState.collectAsStateWithLifecycle()

                // --- NEW: Observe Navigation Events from ViewModel ---
                LaunchedEffect(navController, viewModel) {
                    viewModel.navigaionEvent.collectLatest { navigationEvent ->
                        when (navigationEvent) {
                            is DashboardViewModel.NavigationEvent.ToProductList -> {
                                // CORRECT CALL: Access createRoute directly from Routes.ProductlistUI
                                navController.navigate(
                                    Routes.ProductTypeUI.createRoute(
                                        navigationEvent.categoryId,
                                        navigationEvent.title
                                    )
                                )
                            }

                            is DashboardViewModel.NavigationEvent.ProductDetails -> {
                                navController.navigate(
                                    Routes.ProductDetail.createRoute(
                                        navigationEvent.idItems
                                    )
                                )
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
//            val productId = backStackEntry.arguments?.getString(Routes.ProductDetail.navArgument.first().name)

                val productDetailViewModel = hiltViewModel<ProductDetailViewModel>()
                val productDetailState by productDetailViewModel.productDetailState.collectAsStateWithLifecycle()
                ProductDetailUI(
                    navController,
                    state = productDetailState,
                    event = productDetailViewModel::onEvent
                )

            }

            composable(
                route = Routes.cartUI.route
            ) {
                val cartViewModel = hiltViewModel<CartViewModel>()
                val cartState by cartViewModel.cartState.collectAsStateWithLifecycle()
                CartScreen(navController = navController, state = cartState, event = cartViewModel::onEvent)
            }

            composable (
                route = Routes.ProfileUI.route
            ){
//                val profileViewModel = hiltViewModel<ProfileViewModel>()
//                val profileState by profileViewModel.profileState.collectAsStateWithLifecycle()
//                ProfileScreen(navController = navController, state = profileState, event = profileViewModel::onEvent)
            }
        }


    }
}




