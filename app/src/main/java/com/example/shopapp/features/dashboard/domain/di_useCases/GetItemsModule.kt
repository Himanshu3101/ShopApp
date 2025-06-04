package com.example.shopapp.features.dashboard.domain.di_useCases

import com.example.shopapp.ui.common.interfaces.ItemRepository
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