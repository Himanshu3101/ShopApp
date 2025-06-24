package com.example.shopapp.core.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.shopapp.core.data.local.dao.CartItemDao
import com.example.shopapp.core.data.local.dao.ItemDao
import com.example.shopapp.core.data.local.entities.CartItemEntity

@Database(entities = [CartItemEntity::class, /*ItemEntity::class*/], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase(){
    abstract fun cartItemDao(): CartItemDao
    abstract fun itemDao(): ItemDao


}