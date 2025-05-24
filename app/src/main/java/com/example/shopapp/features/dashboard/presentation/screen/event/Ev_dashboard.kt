package com.example.shopapp.features.dashboard.presentation.screen.event


//Events
sealed class Ev_dashboard {

    object InitDashboard : Ev_dashboard()
    data class SetProductId(val categoryId: String) : Ev_dashboard()

}