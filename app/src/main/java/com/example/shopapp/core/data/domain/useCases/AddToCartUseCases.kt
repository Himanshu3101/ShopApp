package com.example.shopapp.core.data.domain.useCases

import com.example.shopapp.core.data.domain.model.CartItemDomain
import com.example.shopapp.features.cart.domain.repository.CartRepository
import javax.inject.Inject


//This is uses for whole app for the saveData in the local database
class AddToCartUseCases @Inject constructor(
    private val cartRepository: CartRepository
){
    suspend operator fun invoke(item: CartItemDomain) {
        cartRepository.addItemToCart(item)
    }
}