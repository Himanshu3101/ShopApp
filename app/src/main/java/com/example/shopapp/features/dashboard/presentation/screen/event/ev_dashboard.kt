package com.example.shopapp.features.dashboard.presentation.screen.event


//Events
sealed class ev_dashboard {

    object InitDashboard : ev_dashboard()
    data class setProductId(val categoryId: String) : ev_dashboard()

}