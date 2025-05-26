package com.example.shopapp.features.dashboard.data.repository

import com.example.shopapp.core.network.ShopApi
import com.example.shopapp.features.dashboard.domain.remote.model.CategoryDomain
import com.example.shopapp.features.dashboard.domain.remote.repositoy.CategoryRepository

class CategoryRepositoryImpl (private val api : ShopApi) : CategoryRepository{

    override suspend fun getCategory(): List<CategoryDomain> {
        return api.getCategory().results
    }
}