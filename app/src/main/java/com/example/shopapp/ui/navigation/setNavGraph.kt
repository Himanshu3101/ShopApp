package com.example.shopapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.compose.runtime.getValue
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.shopapp.features.dashboard.presentation.screen.Dashboard
import com.example.shopapp.features.dashboard.presentation.screen.DashboardViewModel
import com.example.shopapp.features.introScreen.presentation.IntroScreen
import com.example.shopapp.features.productList.ProductListUI

@Composable
fun SetNavGraph() {

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination =Routes.IntoScreen.route) {
        composable(route = Routes.IntoScreen.route) {
            IntroScreen(navController)
        }

        composable(route = Routes.Dashboard.route) {

            val viewModel = hiltViewModel<DashboardViewModel>()
            val state by viewModel.dashboardState.collectAsState()
            Dashboard(navController,event = viewModel::onEvent, state = state)
        }

        composable ( route = Routes.ProductlistUI.route) {
            ProductListUI(navController)
        }
    }



}


