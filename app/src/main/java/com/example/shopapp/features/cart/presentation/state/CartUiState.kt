package com.example.shopapp.features.cart.presentation.state

import com.example.shopapp.core.data.domain.model.CartItemDomain

data class CartUiState(
    val cartItems: List<CartItemDomain> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

