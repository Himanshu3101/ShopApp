package com.example.shopapp.core.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart_items")
data class CartItemEntity(
    @PrimaryKey(autoGenerate = true) val id : Int = 0,
    val idItems:String,
    val imageUrl: String,
    val price: Double,
    val title: String,
    val categoryId : String,
    val quantity:Int
)