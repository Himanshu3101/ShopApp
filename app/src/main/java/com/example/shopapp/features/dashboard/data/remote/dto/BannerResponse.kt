package com.example.shopapp.features.dashboard.data.remote.dto

import com.example.shopapp.features.dashboard.domain.remote.model.Banner

data class BannerResponse(
    val results: List<Banner>
)