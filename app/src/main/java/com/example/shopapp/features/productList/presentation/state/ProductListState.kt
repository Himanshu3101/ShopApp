package com.example.shopapp.features.productList.presentation.state

import com.example.shopapp.features.common.interfaces.DisplayableItem


// UI State for Product List screen (already defined)
data class ProductListState(
    val isLoading : Boolean = false,
    val errorMsg: String? = null,
    val items: List<ProductListUiModel> = emptyList(),
    val categoryId : String = "",
    val title:String = ""
)

data class ProductListUiModel(
    override val imageUrl: List<String> = emptyList(),
    override val price: Int,
    override val rating: Double,
    override val title: String,
    override val categoryId : String,
    override val showRecommended : Boolean,
    override val idItems: Int,
    override val description: String,
    override val cartQuantity: Int
) : DisplayableItem