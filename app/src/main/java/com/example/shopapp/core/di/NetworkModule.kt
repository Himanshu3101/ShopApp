package com.example.shopapp.core.di

import com.example.shopapp.core.network.secrets.KeysProvider.AppKeysProviderImpl
import com.example.shopapp.core.network.secrets.AuthInterceptor
import com.example.shopapp.core.network.ShopApi
import com.example.shopapp.core.network.secrets.KeysProvider.KeysProvider
import com.example.shopapp.features.dashboard.data.repository.BannerRepositoryImpl
import com.example.shopapp.features.dashboard.domain.remote.repositoy.BannerRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(authInterceptor: AuthInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideApi(client: OkHttpClient): ShopApi/*Retrofit*/ {
        return Retrofit.Builder()
            .baseUrl("https://parseapi.back4app.com/classes/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ShopApi::class.java)
    }
}
