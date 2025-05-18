package com.example.shopapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.shopapp.features.dashboard.presentation.screen.Dashboard
import com.example.shopapp.features.introScreen.presentation.IntroScreen

@Composable
fun SetNavGraph() {

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination =Routes.IntoScreen.route) {
        composable(route = Routes.IntoScreen.route) {
            IntroScreen(navController)
        }

        composable(route = Routes.Dashboard.route) {

            /*val viewModel = hiltViewModel<dashboardViewModel>()*/

            Dashboard(navController/*,event = viewModel::onEvent*/)
        }
    }



}


