package com.example.shopapp.features.cart.domain.repository

import com.example.shopapp.core.data.domain.model.CartItemDomain
import kotlinx.coroutines.flow.Flow

interface CartRepository {
    fun getCartItems(): Flow<List<CartItemDomain>>
    suspend fun addItemToCart(item: CartItemDomain)
    suspend fun updateItemToCart(item: CartItemDomain)
    suspend fun removeItemFromCart(idItems: String)
    suspend fun clearCart()
}