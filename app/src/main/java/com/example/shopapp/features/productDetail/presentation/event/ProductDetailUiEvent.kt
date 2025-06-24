package com.example.shopapp.features.productDetail.presentation.event

sealed class ProductDetailUiEvent {
    data class setMainImage(val imageUrl: String) : ProductDetailUiEvent()
    data class setQuantity(val quantity:Int) : ProductDetailUiEvent()
    object onAddToCart : ProductDetailUiEvent()
}