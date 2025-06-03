package com.example.shopapp.features.productList.presentation.event

sealed class ProductListUiEvent {
    data class SetCategoryId(val categoryId: String) : ProductListUiEvent()
    object InitProductList : ProductListUiEvent()
}