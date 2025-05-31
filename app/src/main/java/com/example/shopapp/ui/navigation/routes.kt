package com.example.shopapp.ui.navigation

const val selectedCategory = "selected_category"


sealed class Routes(val route: String) {

    object IntoScreen : Routes(route = "introScreen")

    object Dashboard : Routes(route = "dashboard")

    object ProductlistUI : Routes(route = "listOfProductUi/{$selectedCategory}") {
        fun passId(categoryId: String) = "listOfProductUi/$categoryId"
    }


}