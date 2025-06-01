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
        route = "listOfProductUi/{$CATEGORY_ID_ARG}",
        navArgument = listOf(
            navArgument(CATEGORY_ID_ARG) {type = NavType.StringType}
        )
    ){   // Helper function to create the actual route string with values
        // This function is *only* available via Routes.ProductlistUI.createRoute(...)
        fun createRoute(categoryId: String): String {
            return "productListUi/$categoryId"
        }
    }


    /*// Route for product details (e.g., when clicking on an item in ProductListUI)
    object ProductDetail : Routes(
        route = "productDetail/{$PRODUCT_ID_ARG}", // Use the new constant
        navArguments = listOf(navArgument(PRODUCT_ID_ARG) { type = NavType.StringType })
    ) {
        // Helper function to create the actual route string with values
        // This function is *only* available via Routes.ProductDetail.createRoute(...)
        fun createRoute(productId: String) = "productDetail/$productId"
    }

    // If you had other routes that needed arguments, you would define similar
    // inner `createRoute` functions within their respective object declarations.*/

}