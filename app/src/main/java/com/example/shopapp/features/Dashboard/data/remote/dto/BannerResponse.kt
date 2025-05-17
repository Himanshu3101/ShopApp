package com.example.shopapp.features.Dashboard.data.remote.dto

import com.example.shopapp.features.Dashboard.domain.remote.model.Banner

data class BannerResponse(
    val results: List<Banner>
)