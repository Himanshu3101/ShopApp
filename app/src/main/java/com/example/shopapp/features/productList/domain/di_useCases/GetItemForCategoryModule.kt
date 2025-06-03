package com.example.shopapp.features.productList.domain.di_useCases

import com.example.shopapp.features.dashboard.domain.remote.repositoy.ItemRepository
import com.example.shopapp.features.productList.domain.useCases.GetItemForCategoryUC
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)

object GetItemForCategoryModule {

    @Provides
    @Singleton
    fun provideGetItemForCategoryUseCases(itemRepository: ItemRepository):GetItemForCategoryUC{
        return GetItemForCategoryUC(itemRepository)

    }
}