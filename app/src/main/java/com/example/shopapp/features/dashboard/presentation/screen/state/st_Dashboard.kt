package com.example.shopapp.features.dashboard.presentation.screen.state


//State
data class st_Dashboard(
    val bannerUrls: List<String> = emptyList(),
    val isLoading: Boolean = false,
    val categoryList: List<CategoryDetails> = emptyList(),
    val setProductId : String ?= "0",
    val itemsState : List<ItemData> = emptyList(),
    //For initalizing the dashboard
    val isInitialized : Boolean = false
)

data class ItemData(
    val imageUrl: List<String> = emptyList(),
    val price: Int,
    val rating: Double,
    val title: String,
    val categoryId : Int,
    val showRecommended : Boolean
)

data class CategoryDetails(
    val Pid: Int,
    val title: String,
)


