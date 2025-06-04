package com.example.shopapp.features.dashboard.di

import com.example.shopapp.core.network.ShopApi
import com.example.shopapp.ui.common.interfaces.implementation.ItemRepositoryImpl
import com.example.shopapp.ui.common.interfaces.ItemRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ItemModule {

    @Provides
    @Singleton
    fun provideItemRepository(api : ShopApi): ItemRepository{
        return ItemRepositoryImpl(api)
    }
}