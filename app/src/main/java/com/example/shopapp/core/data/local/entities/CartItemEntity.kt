package com.example.shopapp.core.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart_items")
data class CartItemEntity(
    @PrimaryKey(autoGenerate = true) val idItems:Int,
    val imageUrl: String,
    val price: Double,
    val title: String,
    val categoryId : String,
    val quantity:Int
)