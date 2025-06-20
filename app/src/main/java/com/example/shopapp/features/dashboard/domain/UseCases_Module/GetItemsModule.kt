package com.example.shopapp.features.dashboard.domain.UseCases_Module

import com.example.shopapp.features.dashboard.domain.remote.repositoy.ItemRepository
import com.example.shopapp.features.dashboard.domain.useCases.GetItems_UC
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object GetItemsModule {

    @Provides
    @Singleton
    fun provideGetItemUseCases(itemRepository: ItemRepository) : GetItems_UC {
        return GetItems_UC(itemRepository)
    }
}