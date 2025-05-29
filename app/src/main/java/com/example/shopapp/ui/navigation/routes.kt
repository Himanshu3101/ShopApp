package com.example.shopapp.ui.navigation

sealed class Routes(val route:String){

    object IntoScreen : Routes(route = "introScreen")

    object Dashboard : Routes(route = "dashboard")

    object ProductlistUI : Routes(route = "listOfProductUi")
}