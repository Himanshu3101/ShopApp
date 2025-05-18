package com.example.shopapp.features.dashboard.presentation.screen.state

import com.example.shopapp.features.dashboard.domain.remote.model.Banner


//State
data class st_Dashboard(
    val bannerUrls: List<String> = emptyList(),
    val isLoading: Boolean = false,
    val categoryList: List<String> = emptyList()

)




