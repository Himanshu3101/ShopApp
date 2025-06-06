package com.example.shopapp.core.network.cache

import com.example.shopapp.features.dashboard.domain.remote.model.ItemDomain
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

object InMemoryItemCache{
    private var allItems : List<ItemDomain>? = null
    private val mutex = Mutex()   // To ensure thread-safe access to 'allItems'

    suspend fun saveAllItems(items : List<ItemDomain>){
        mutex.withLock {
            this.allItems = items
        }
    }

    suspend fun getAllItems(): List<ItemDomain>?{
       return mutex.withLock {
           this.allItems
       }
    }

    suspend fun clearCache(){
        mutex.withLock {
            allItems = null
        }
    }
}