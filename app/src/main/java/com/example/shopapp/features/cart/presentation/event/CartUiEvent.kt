package com.example.shopapp.features.cart.presentation.event

sealed class CartUiEvent {
//    object InitCart: CartUiEvent()
    data class ItemClicked(val idItems: Int) : CartUiEvent()

//    data class SetProductType(val categoryId: String, val title : String) : CartUiEvent()

}