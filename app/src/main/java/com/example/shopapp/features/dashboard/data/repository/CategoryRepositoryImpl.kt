package com.example.shopapp.features.dashboard.data.repository

import com.example.shopapp.core.network.ShopApi
import com.example.shopapp.features.dashboard.domain.remote.model.Category
import com.example.shopapp.features.dashboard.domain.remote.repositoy.CategoryRepository

class CategoryRepositoryImpl (private val api : ShopApi) : CategoryRepository{

    override suspend fun getCategory(): List<Category> {
        return api.getCategory().results
    }
}