package com.example.shopapp.features.dashboard.di

import com.example.shopapp.core.network.ShopApi
import com.example.shopapp.features.dashboard.data.repository.BannerRepositoryImpl
import com.example.shopapp.features.dashboard.domain.remote.repositoy.BannerRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object BannerModule {

    @Provides
    @Singleton
    fun provideBannerRepository(api: ShopApi): BannerRepository {
        return BannerRepositoryImpl(api)
    }
}