package com.example.shopapp.core.di

import com.example.shopapp.core.network.ShopApi
import com.example.shopapp.core.network.secrets.AuthInterceptor
import com.example.shopapp.core.network.secrets.KeysProvider.AppKeysProviderImpl
import com.example.shopapp.core.network.secrets.KeysProvider.KeysProvider
import com.example.shopapp.features.dashboard.data.repository.BannerRepositoryImpl
import com.example.shopapp.features.dashboard.domain.remote.repositoy.BannerRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideKeysProvider(): KeysProvider = AppKeysProviderImpl()

    @Provides
    @Singleton
    fun provideAuthInterceptor(keysProvider: KeysProvider): AuthInterceptor =
        AuthInterceptor(keysProvider)


}