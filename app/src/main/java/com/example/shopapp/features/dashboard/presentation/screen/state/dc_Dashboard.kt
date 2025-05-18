package com.example.shopapp.features.dashboard.presentation.screen.state

import com.example.shopapp.features.dashboard.domain.remote.model.Banner

data class dc_Dashboard(

    val banners : List<Banner> = emptyList(),
    val isLoading : Boolean = false,
)


