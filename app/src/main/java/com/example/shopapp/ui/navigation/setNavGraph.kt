package com.example.shopapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.shopapp.features.Dashboard.presentation.screen.Dashboard
import com.example.shopapp.features.IntroScreen.presentation.IntroScreen

@Composable
fun SetNavGraph() {

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination =Routes.IntoScreen.route) {
        composable(route = Routes.IntoScreen.route) {
            IntroScreen(navController)
        }

        composable(route = Routes.Dashboard.route) {
            Dashboard(navController)
        }
    }



}


