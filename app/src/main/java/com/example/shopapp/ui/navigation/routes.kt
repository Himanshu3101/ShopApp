package com.example.shopapp.ui.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument

    const val CATEGORY_ID_ARG = "selected_category"


    sealed class Routes(
        val route: String,
        val navArgument:List<NamedNavArgument> = emptyList()  // Define arguments for NavHost
    ) {

        object IntoScreen : Routes(route = "introScreen")

        object Dashboard : Routes(route = "dashboard")

        object ProductlistUI : Routes(
            route = "productListUi/{$CATEGORY_ID_ARG}",
            navArgument = listOf(
                navArgument(CATEGORY_ID_ARG) {type = NavType.StringType}
            )
        ){   // Helper function to create the actual route string with values
            // This function is *only* available via Routes.ProductlistUI.createRoute(...)
            fun createRoute(categoryId: String): String {
                return "productListUi/$categoryId"
            }
        }

        // Assuming you have ProductDetail too (good practice to define it here)
            /*object ProductDetail : Routes(
                route = "productDetail/{productId}", // Example route
                navArgument = listOf(
                    navArgument("productId") { type = NavType.StringType }
                )
            ) {
                fun createRoute(productId: String): String {
                    return "productDetail/$productId"
                }
            }*/

}