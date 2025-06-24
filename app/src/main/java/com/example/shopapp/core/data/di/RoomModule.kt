package com.example.shopapp.core.data.di

import android.content.Context
import androidx.room.Room
import com.example.shopapp.core.data.local.database.AppDatabase
import com.example.shopapp.core.util.Constants
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
object RoomModule{

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            Constants.DATABASE_NAME
        ).build()
    }

    @Provides
    fun provideCartItemDao(database: AppDatabase) = database.cartItemDao()

//    @Provides
//    fun provideItemDao(database: AppDatabase) = database.itemDao()
}