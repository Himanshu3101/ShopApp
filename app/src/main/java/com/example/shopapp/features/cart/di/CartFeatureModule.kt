package com.example.shopapp.features.cart.di

import com.example.shopapp.core.data.repository.CartRepositoryImpl
import com.example.shopapp.features.cart.domain.repository.CartRepository
import com.example.shopapp.features.cart.domain.useCases.GetCartItemsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CartFeatureModule {

    @Provides
    @Singleton
    fun provideGetCartItemsUseCase(repository: CartRepository): GetCartItemsUseCase = GetCartItemsUseCase(repository)
}