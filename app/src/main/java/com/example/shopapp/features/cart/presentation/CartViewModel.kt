package com.example.shopapp.features.cart.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopapp.core.data.domain.model.CartItemDomain
import com.example.shopapp.core.network.Resources
import com.example.shopapp.features.cart.domain.useCases.GetCartItemsUseCase
import com.example.shopapp.features.cart.presentation.event.CartUiEvent
import com.example.shopapp.features.cart.presentation.state.CartUiState
import com.example.shopapp.features.dashboard.di.IoDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val getCartItemsUseCase: GetCartItemsUseCase,
    @IoDispatcher private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
) : ViewModel(){

    private val _cartState = MutableStateFlow(CartUiState())
    val cartState = _cartState.asStateFlow()



    init {
        initCartItem()
    }


    fun onEvent(event: CartUiEvent){
        when(event){
//            CartUiEvent.InitCart -> initCartItem()
            is CartUiEvent.ItemClicked ->  TODO()
        }
    }

    private fun initCartItem() {
        viewModelScope.launch(dispatcher) {
            _cartState.update { it.copy(isLoading = true, errorMessage = null) }

            getCartItemsUseCase.invoke().collectLatest{ resources ->
                _cartState.update { currentState ->
                    when (resources) {
                        is Resources.Loading -> {
                            currentState.copy(isLoading = true, errorMessage = null)
                        }


                        is Resources.Success -> currentState.copy(
                            isLoading = false,
                            errorMessage = null,
                            cartItems = resources.data?.map { itemDomain ->
                                CartItemDomain(
                                    idItems = itemDomain.idItems,
                                    imageUrl = itemDomain.imageUrl,
                                    price = itemDomain.price,
                                    title = itemDomain.title,
                                    categoryId = itemDomain.categoryId,
                                    quantity = itemDomain.quantity,
                                )
                            } ?: emptyList(),
                        )
                        is Resources.Error -> currentState.copy(
                            isLoading = false, // Loading is complete (with an error)
                            errorMessage = resources.message ?: "An unexpected error occurred",
                            cartItems = emptyList()
                        )
                    }
                }
            }
        }
    }
}