package com.example.shopapp.features.dashboard.data.remote.dto

import com.example.shopapp.features.dashboard.domain.remote.model.Items

data class ItemsResponse(
    val results: List<Items>
)