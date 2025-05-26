package com.example.shopapp.features.dashboard.data.remote.dto

import com.example.shopapp.features.dashboard.domain.remote.model.CategoryDomain

data class CategoryResponse(
    val results: List<CategoryDomain>
)