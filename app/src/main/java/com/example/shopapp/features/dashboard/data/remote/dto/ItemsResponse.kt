package com.example.shopapp.features.dashboard.data.remote.dto

import com.example.shopapp.features.dashboard.domain.remote.model.ItemDomain

data class ItemsResponse(
    val results: List<ItemDomain>
)