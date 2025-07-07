package com.example.shopapp.features.cart.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowColumn
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.shopapp.R
import com.example.shopapp.features.cart.presentation.event.CartUiEvent
import com.example.shopapp.features.cart.presentation.state.CartUiState
import com.example.shopapp.features.common.components.AppBar
import com.example.shopapp.ui.navigation.Routes
import com.example.shopapp.ui.theme.Dimens

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

            Spacer(modifier = Modifier.height(Dimens.SmallPadding))

            Column (
                modifier = Modifier.padding(Dimens.SmallPadding)
            ){
                FlowColumn (
                    modifier = Modifier
                        .height(Dimens.MediumBoxHeight)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(Dimens.SmallCornerRadius))
                        .background (colorResource(R.color.blue)),
                ){
                    state.cartItems.forEach { item ->
                        Row(
                            modifier = Modifier
                                .padding(Dimens.SmallPadding)
                                .fillMaxWidth()
                                .fillMaxHeight(),
                            verticalAlignment = Alignment.CenterVertically

                        ){
                            Text(
                                text = item.title,
                                color = colorResource(R.color.white)
                            )
                        }
                    }
                }
            }
        }
    }
}