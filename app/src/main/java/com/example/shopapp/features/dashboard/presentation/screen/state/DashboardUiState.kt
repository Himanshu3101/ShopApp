package com.example.shopapp.features.dashboard.presentation.screen.state

import com.example.shopapp.features.common.interfaces.DisplayableItem


//State
data class DashboardUiState(
    val bannerUrls: List<String> = emptyList(),
    val isLoading: Boolean = false,
    val categoryList: List<CategoryDetails> = emptyList(),
//    val selectedProductId : String ?= "0",
    val itemsState : List<ItemData> = emptyList(),
    //For initalizing the dashboard
    val isInitialized : Boolean = false,

    // NEW: To hold combined error message
    val errorMessage: String? = null
)

data class ItemData(
    override val imageUrl: List<String> = emptyList(),
    override val price: Int,
    override val rating: Double,
    override val title: String,
    override val categoryId : String,
    override val showRecommended : Boolean,
    override val idItems: Int,
    override val description: String
) : DisplayableItem

data class CategoryDetails(
    val id: Int,
    val title: String,
)


