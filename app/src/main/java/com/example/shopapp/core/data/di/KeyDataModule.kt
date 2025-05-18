package com.example.shopapp.core.data.di

import com.example.shopapp.core.network.secrets.AuthInterceptor
import com.example.shopapp.core.network.secrets.KeysProvider.AppKeysProviderImpl
import com.example.shopapp.core.network.secrets.KeysProvider.KeysProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object KeyDataModule {

    @Provides
    @Singleton
    fun provideKeysProvider(): KeysProvider = AppKeysProviderImpl()

    @Provides
    @Singleton
    fun provideAuthInterceptor(keysProvider: KeysProvider): AuthInterceptor =
        AuthInterceptor(keysProvider)


}