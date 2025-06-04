package com.example.shopapp.features.productList.domain.di_useCases

import com.example.shopapp.ui.common.interfaces.ItemRepository
import com.example.shopapp.features.productList.domain.useCases.GetProductTypeUC
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)

object GetProductTypeModule {

    @Provides
    @Singleton
    fun provideGetItemForCategoryUseCases(itemRepository: ItemRepository):GetProductTypeUC{
        return GetProductTypeUC(itemRepository)

    }
}