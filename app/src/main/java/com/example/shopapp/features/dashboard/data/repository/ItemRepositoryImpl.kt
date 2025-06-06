package com.example.shopapp.features.dashboard.data.repository

import com.example.shopapp.core.network.ShopApi
import com.example.shopapp.features.dashboard.domain.remote.model.ItemDomain
import com.example.shopapp.features.dashboard.domain.remote.repositoy.ItemRepository
import com.example.shopapp.core.network.cache.InMemoryItemCache

class ItemRepositoryImpl (private val api: ShopApi) : ItemRepository {
    override suspend fun getItems(): List<ItemDomain> {
        // 1. Try to get data from in-memory cache
        val cachedItems = InMemoryItemCache.getAllItems()
        if (cachedItems != null) {
            return cachedItems
        }
        // 2. If cache is empty, simulate a network call to fetch all items
        val itemsFromNetwork = api.getItems().results

        // 3. Save the fetched data to the in-memory cache
        InMemoryItemCache.saveAllItems(itemsFromNetwork)
        return itemsFromNetwork
    }

    override suspend fun getItemsByCategoryId(categoryId: String): List<ItemDomain> {
        val allItems = getItems()
        return allItems.filter { it.categoryId.toString() == categoryId }
    }
}