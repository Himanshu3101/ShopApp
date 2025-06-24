package com.example.shopapp.features.cart.domain.useCases

import com.example.shopapp.core.data.domain.model.CartItemDomain
import com.example.shopapp.features.cart.domain.repository.CartRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCartItemsUseCase @Inject constructor(
    private val cartRepository: CartRepository
){
    operator fun invoke(): Flow<List<CartItemDomain>> {
        return cartRepository.getCartItems()
    }

}