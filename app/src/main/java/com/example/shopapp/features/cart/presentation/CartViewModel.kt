package com.example.shopapp.features.cart.presentation

import androidx.lifecycle.ViewModel
import com.example.shopapp.core.data.domain.model.CartItemDomain
import com.example.shopapp.core.network.Resources
import com.example.shopapp.features.cart.presentation.event.CartUiEvent
import com.example.shopapp.features.cart.presentation.state.CartUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class CartViewModel @Inject constructor() : ViewModel(){

    private val _cartItems = MutableStateFlow<Resources<List<CartItemDomain>>>(Resources.Loading())

    private val _cartState = MutableStateFlow(CartUiState())
    val cartState = _cartState.asStateFlow()

    fun onEvent(event: CartUiEvent){
        when(event){
            CartUiEvent.InitCart -> TODO()
            is CartUiEvent.ItemClicked -> TODO()
        }
    }
}