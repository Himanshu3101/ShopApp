package com.example.shopapp.features.dashboard.data.repository

import com.example.shopapp.core.network.ShopApi
import com.example.shopapp.features.dashboard.domain.remote.model.BannerDomain
import com.example.shopapp.features.dashboard.domain.remote.repositoy.BannerRepository

class BannerRepositoryImpl(private val api: ShopApi) : BannerRepository {

    override suspend fun getBanners(): List<BannerDomain> {
        return api.getBanners().results
    }

}