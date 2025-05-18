package com.example.shopapp.features.dashboard.di

import com.example.shopapp.core.network.ShopApi
import com.example.shopapp.features.dashboard.data.repository.CategoryRepositoryImpl
import com.example.shopapp.features.dashboard.domain.remote.repositoy.CategoryRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CategoryModule {

    @Provides
    @Singleton
    fun provideCategoryRepository(api: ShopApi): CategoryRepository {
        return CategoryRepositoryImpl(api)
    }
}