package com.example.shopapp.features.common.interfaces

interface DisplayableItem{
    val idItems:Int
    val imageUrl: List<String>
    val price: Int
    val rating: Double
    val title: String
    val description: String
    val categoryId : String
    val showRecommended : Boolean
    val cartQuantity : Int
}