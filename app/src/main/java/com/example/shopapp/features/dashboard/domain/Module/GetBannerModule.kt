package com.example.shopapp.features.dashboard.domain.Module

import com.example.shopapp.features.dashboard.domain.remote.repositoy.BannerRepository
import com.example.shopapp.features.dashboard.domain.useCases.GetBanner_UC
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object GetBannerModule {

    @Provides
    @Singleton
    fun provideGetBannerUseCases(bannerRepository: BannerRepository): GetBanner_UC {
        return GetBanner_UC(bannerRepository)
    }
}