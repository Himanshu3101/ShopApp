package com.example.shopapp.core.network

import com.example.shopapp.features.dashboard.data.remote.dto.BannerResponse
import com.example.shopapp.features.dashboard.data.remote.dto.CategoryResponse
import retrofit2.http.GET

interface ShopApi {

    @GET("Banner")
    suspend fun getBanners(): BannerResponse

    @GET("Category")
    suspend fun getCategory(): CategoryResponse

}