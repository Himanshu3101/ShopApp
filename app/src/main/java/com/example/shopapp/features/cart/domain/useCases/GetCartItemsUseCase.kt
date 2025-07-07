package com.example.shopapp.features.cart.domain.useCases

import com.example.shopapp.core.data.domain.model.CartItemDomain
import com.example.shopapp.core.network.Resources
import com.example.shopapp.features.cart.domain.repository.CartRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetCartItemsUseCase @Inject constructor(
    private val cartRepository: CartRepository
){
    operator fun invoke(): Flow<Resources<List<CartItemDomain>>> = flow {
        emit(Resources.Loading())

        cartRepository.getCartItems()
            .map { data-> Resources.Success(data) }
            .catch { e-> emit(Resources.Error(e.message?:"An unexpected error occurred" )) }
            .collect{emit(it)}
    }

}