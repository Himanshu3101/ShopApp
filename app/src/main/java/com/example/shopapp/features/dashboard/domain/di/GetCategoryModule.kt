package com.example.shopapp.features.dashboard.domain.di

import com.example.shopapp.features.dashboard.domain.remote.repositoy.CategoryRepository
import com.example.shopapp.features.dashboard.domain.useCases.GetCategory_UC
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object GetCategoryModule {

    @Provides
    @Singleton
    fun provideGetCategoryUseCases(categoryRepository: CategoryRepository): GetCategory_UC {
        return GetCategory_UC(categoryRepository)
    }
}