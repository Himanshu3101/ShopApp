package com.example.shopapp.core.data.repository

import com.example.shopapp.core.data.local.dao.CartItemDao
import com.example.shopapp.core.data.local.entities.CartItemEntity
import com.example.shopapp.core.data.domain.model.CartItemDomain
import com.example.shopapp.features.cart.domain.repository.CartRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CartRepositoryImpl @Inject constructor(
    private val cartItemDao: CartItemDao
) : CartRepository{

    override fun getCartItems(): Flow<List<CartItemDomain>> {
        return cartItemDao.getAllItems().map {entities->
            entities.map{it.toDomain()}
        }
    }

    override suspend fun addItemToCart(item: CartItemDomain) {
        val existingCartItem = cartItemDao.getCartItemByItemId(item.idItems.toString())
        if (existingCartItem != null) {
            val updatedCartItem = existingCartItem.quantity + item.quantity
            cartItemDao.updateCartItems(existingCartItem.copy(quantity = updatedCartItem))
        } else {
            cartItemDao.insertCartItem(item.toEntity())
        }
    }

    override suspend fun updateItemToCart(item: CartItemDomain) {
        cartItemDao.updateCartItems(item.toEntity())
    }

    override suspend fun removeItemFromCart(idItems: String) {
        val entityToDelete = cartItemDao.getCartItemByItemId(idItems)
        entityToDelete?.let{cartItemDao.deleteCartItem(it)}
    }

    override suspend fun clearCart() {
        // Add a clear all query to your CartItemDao if needed
        // cartItemDao.clearAllCartItems()
    }

}

private fun CartItemDomain.toEntity(): CartItemEntity {
    return CartItemEntity(idItems = idItems, imageUrl = imageUrl, price = price, title = title, categoryId = categoryId, quantity = quantity)
}

private fun CartItemEntity.toDomain(): CartItemDomain {
    return CartItemDomain(idItems, imageUrl, price, title, categoryId, quantity)
}
