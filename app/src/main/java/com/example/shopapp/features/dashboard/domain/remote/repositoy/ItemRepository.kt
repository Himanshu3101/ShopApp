package com.example.shopapp.features.dashboard.domain.remote.repositoy

import com.example.shopapp.features.dashboard.domain.remote.model.ItemDomain

interface ItemRepository {

    suspend fun getItems() : List<ItemDomain>

}