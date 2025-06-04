package com.example.shopapp.features.dashboard.presentation.screen.event


//Events
sealed class DashboardUiEvent {

    object InitDashboard : DashboardUiEvent()
    data class SetProductType(val categoryId: String, val title : String) : DashboardUiEvent()

}