package com.example.shopapp.core.data.di

import com.example.shopapp.core.data.domain.useCases.AddToCartUseCases
import com.example.shopapp.core.data.repository.CartRepositoryImpl
import com.example.shopapp.features.cart.domain.repository.CartRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CoreDomainModule{

    // Provide CartRepositoryImpl as CartRepository interface
    @Provides
    @Singleton
    fun provideCartRepository(cartRepositoryImpl: CartRepositoryImpl): CartRepository = cartRepositoryImpl

    // Provide the AddToCartUseCase which is now in core/domain
    @Provides
    @Singleton
    fun provideAddToCartuseCases(repository: CartRepository): AddToCartUseCases = AddToCartUseCases(repository)
}