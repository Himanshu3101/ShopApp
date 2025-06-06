package com.example.shopapp.features.productDetail.presentation.state

import com.example.shopapp.features.common.interfaces.DisplayableItem

data class ProductDetailState(
    val isLoading : Boolean = false,
    val errorMsg: String? = null,
    val items: List<ProductDetailUiModel> = emptyList(),
)

data class ProductDetailUiModel(
    override val idItems:Int,
    override val imageUrl: List<String> = emptyList(),
    override val price: Int,
    override val title: String,
    override val description: String,
    override val rating: Double,
    override val categoryId: String,
    override val showRecommended: Boolean,
): DisplayableItem