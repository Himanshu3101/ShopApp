package com.example.shopapp.ui.common.interfaces

import com.example.shopapp.features.dashboard.domain.remote.model.ItemDomain

interface ItemRepository {

    suspend fun getItems() : List<ItemDomain>
    suspend fun getItemsByCategoryId(categoryId : String) : List<ItemDomain>
}