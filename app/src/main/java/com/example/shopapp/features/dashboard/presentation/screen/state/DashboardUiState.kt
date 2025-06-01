package com.example.shopapp.features.dashboard.presentation.screen.state


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
    val imageUrl: List<String> = emptyList(),
    val price: Int,
    val rating: Double,
    val title: String,
    val categoryId : String,
    val showRecommended : Boolean
)

data class CategoryDetails(
    val id: Int,
    val title: String,
)


