package com.example.shopapp.core.data.domain.model

data class CartItemDomain(
    val idItems:String,
    val imageUrl: String,
    val price: Double,
    val title: String,
    val categoryId : String,
    val quantity:Int
)