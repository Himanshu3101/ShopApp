package com.example.shopapp.features.common.interfaces

interface DisplayableItem{
    val imageUrl: List<String>
    val price: Int
    val rating: Double
    val title: String
    val categoryId : String
    val showRecommended : Boolean
}