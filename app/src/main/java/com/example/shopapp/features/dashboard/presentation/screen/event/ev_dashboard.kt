package com.example.shopapp.features.dashboard.presentation.screen.event


//Events
sealed class ev_dashboard {

    object getBanners : ev_dashboard()
    object getCategory : ev_dashboard()
}