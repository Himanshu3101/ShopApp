package com.example.shopapp.features.dashboard.presentation.screen.state

data class dc_Dashboard(

    val isLoading : Boolean = false,
    val banners : List<Banner> = emptyList(),
)


