package com.example.shopapp.features.dashboard.data.repository

import com.example.shopapp.core.network.ShopApi
import com.example.shopapp.features.dashboard.domain.remote.model.ItemDomain
import com.example.shopapp.features.dashboard.domain.remote.repositoy.ItemRepository

class ItemRepositoryImpl (private val api: ShopApi) : ItemRepository {
    override suspend fun getItems(): List<ItemDomain> {
        return api.getItems().results
    }
}