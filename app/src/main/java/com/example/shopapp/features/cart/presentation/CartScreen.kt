package com.example.shopapp.features.cart.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.shopapp.R
import com.example.shopapp.features.cart.presentation.event.CartUiEvent
import com.example.shopapp.features.cart.presentation.state.CartUiState
import com.example.shopapp.features.common.components.AppBar
import com.example.shopapp.ui.navigation.Routes

@Preview
@Composable
fun Prev_CartUI(){
    CartScreen(
        navController = rememberNavController(),
        state = CartUiState(),
        event = {}
    )
}

@Composable
fun CartScreen(
    navController: NavHostController,
    state: CartUiState,
    event: (CartUiEvent) -> Unit
){
    Box(
        modifier = Modifier.fillMaxSize()
    ){

        Column (
            modifier = Modifier.fillMaxSize().background(colorResource(R.color.white))
        ){

            //Topbar
            AppBar(title = "My Cart") {
                navController.navigate(Routes.Dashboard.route){
                    popUpTo(Routes.Dashboard.route){inclusive = true}
                }
            }
        }
    }
}