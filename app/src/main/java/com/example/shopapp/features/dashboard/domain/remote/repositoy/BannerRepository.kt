package com.example.shopapp.features.dashboard.domain.remote.repositoy

import com.example.shopapp.features.dashboard.domain.remote.model.Banner

interface BannerRepository {

    suspend fun getBanners() : List<Banner>
}