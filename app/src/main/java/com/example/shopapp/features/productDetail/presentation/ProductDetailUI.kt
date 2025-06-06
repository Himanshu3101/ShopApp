package com.example.shopapp.features.productDetail.presentation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.shopapp.features.productDetail.presentation.event.ProductDetailUiEvent
import com.example.shopapp.features.productDetail.presentation.state.ProductDetailState

@Composable
fun ProductDetailUI(
    navController: NavHostController,
    state: ProductDetailState,
    event: (ProductDetailUiEvent) -> Unit
) {

}