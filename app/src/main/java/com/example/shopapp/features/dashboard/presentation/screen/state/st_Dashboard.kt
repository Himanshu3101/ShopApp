package com.example.shopapp.features.dashboard.presentation.screen.state



//State
data class st_Dashboard(
    val bannerUrls: List<String> = emptyList(),
    val isLoading: Boolean = false,
    val categoryList: List<String> = emptyList(),

    val itemsState : List<ItemData> = emptyList(),
    )



data class ItemData(
    val imageUrl: List<String> = emptyList(),
    val price: Int,
    val rating: Double,
    val title: String
)



