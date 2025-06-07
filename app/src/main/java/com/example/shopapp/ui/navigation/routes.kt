package com.example.shopapp.ui.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument

const val CATEGORY_ID_ARG = "selected_category"
const val CATEGORY_TITLE_ARG = "selected_categoryTitle"
const val CATEGORY_PRODUCT_DETAIL_ID_ARG = "idItems"

sealed class Routes(
    val route: String,
    val navArgument: List<NamedNavArgument> = emptyList()  // Define arguments for NavHost
) {

    object IntoScreen : Routes(route = "introScreen")

    object Dashboard : Routes(route = "dashboard")

    object ProductTypeUI : Routes(
        route = "productTypeUi/{$CATEGORY_ID_ARG}/{$CATEGORY_TITLE_ARG}",
        navArgument = listOf(
            navArgument(CATEGORY_ID_ARG) { type = NavType.StringType },
            navArgument(CATEGORY_TITLE_ARG) { type = NavType.StringType }
        )
    ) {   // Helper function to create the actual route string with values
        // This function is *only* available via Routes.ProductlistUI.createRoute(...)
        fun createRoute(categoryId: String, title: String): String {
            return "productTypeUi/$categoryId/$title"
        }
    }


    object ProductDetail : Routes(
        route = "productDetail/{$CATEGORY_PRODUCT_DETAIL_ID_ARG}", // Example route
        navArgument = listOf(
            navArgument("CATEGORY_PRODUCT_DETAIL_ARG") { type = NavType.StringType }
        )
    ) {
        fun createRoute(productId: String): String {
            return "productDetail/$productId"
        }
    }

}