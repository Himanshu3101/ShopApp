package com.example.shopapp.features.dashboard.domain.remote.model

data class ItemDomain(
    val idItems:Int,
    val categoryId: Int,
    val createdAt: String,
    val description: String,
    val objectId: String,
    val picUrl: List<String>,
    val price: Int,
    val rating: Double,
    val showRecommended: Boolean,
    val title: String,
    val updatedAt: String,
    val cartQuantity: Int
)