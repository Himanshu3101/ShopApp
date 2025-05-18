package com.example.shopapp.features.dashboard.domain.remote.repositoy

import com.example.shopapp.features.dashboard.domain.remote.model.Category

interface CategoryRepository {

    suspend fun getCategory() : List<Category>
}