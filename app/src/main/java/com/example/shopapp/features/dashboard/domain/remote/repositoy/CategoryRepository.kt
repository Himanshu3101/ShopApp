package com.example.shopapp.features.dashboard.domain.remote.repositoy

import com.example.shopapp.features.dashboard.domain.remote.model.CategoryDomain

interface CategoryRepository {

    suspend fun getCategory() : List<CategoryDomain>
}