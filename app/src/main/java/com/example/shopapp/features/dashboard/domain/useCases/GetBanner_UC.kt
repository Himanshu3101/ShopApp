package com.example.shopapp.features.dashboard.domain.useCases

import com.example.shopapp.core.network.Resources
import com.example.shopapp.features.dashboard.domain.remote.model.Banner
import com.example.shopapp.features.dashboard.domain.remote.repositoy.BannerRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class GetBanner_UC(
    val bannerRepository: BannerRepository
) {
    operator fun invoke(): Flow<Resources<List<Banner>>> = flow {

        emit(Resources.Loading())

        try {
            emit(Resources.Success(data = bannerRepository.getBanners()))
        } catch (e: Exception) {
            emit(Resources.Error(message = e.message.toString()))
        }

    }.flowOn(Dispatchers.IO)
}