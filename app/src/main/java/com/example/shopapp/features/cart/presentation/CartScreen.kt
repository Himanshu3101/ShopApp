package com.example.shopapp.features.cart.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.shopapp.R
import com.example.shopapp.features.cart.presentation.event.CartUiEvent
import com.example.shopapp.features.cart.presentation.state.CartUiState
import com.example.shopapp.features.common.components.QuantitySelector
import com.example.shopapp.features.common.components.AppBar
import com.example.shopapp.ui.navigation.Routes
import com.example.shopapp.ui.theme.Dimens

@Preview
@Composable
fun Prev_CartUI() {
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
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(colorResource(R.color.white))
        ) {

            //Topbar
            AppBar(title = "My Cart") {
                navController.navigate(Routes.Dashboard.route) {
                    popUpTo(Routes.Dashboard.route) { inclusive = true }
                }
            }

            Spacer(modifier = Modifier.height(Dimens.SmallPadding))


            //CartItems
            CartItems(state, event)

        }
    }
}


@Composable
fun CartItems(state: CartUiState, event: (CartUiEvent) -> Unit) {


    Column(
        modifier = Modifier.padding(Dimens.SmallPadding)
    ) {
        FlowColumn(
            modifier = Modifier
                .fillMaxWidth()

        ) {
            state.cartItems.forEach { item ->
                Row(
                    modifier = Modifier
                        .padding(Dimens.SmallPadding)
                        .height(Dimens.cartItemListSize)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(Dimens.SmallCornerRadius))
                        .background(colorResource(R.color.blue_light)),
//                    verticalAlignment = Alignment.CenterVertically

                ) {
                    Column(
                        modifier = Modifier
                            .padding(Dimens.SmallPadding)
                            .weight(1f)
                            .fillMaxHeight()
                            .clip(RoundedCornerShape(Dimens.SmallCornerRadius))
                            .background(colorResource(R.color.white)),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(item.imageUrl)
                                .build(),
                            contentDescription = "cartItems",
                            contentScale = ContentScale.Inside,
                            modifier = Modifier
                                .height(Dimens.sizeProductItem)
                                .padding(Dimens.SmallBorderWidth)
                        )
                    }


                    Column(
                        modifier = Modifier
                            .padding(Dimens.SmallPadding)
                            .weight(2f)
                            .fillMaxHeight()
                    ) {
                        Text(
                            text = item.title,
                            fontWeight = FontWeight.Bold,
                            fontStyle = FontStyle.Normal,
                            fontSize = Dimens.SmallText,
                            color = colorResource(R.color.black)
                        )

                        Spacer(modifier = Modifier.height(Dimens.SmallPadding))

                        Text(
                            text = "$ ${item.price}",
                        )

//                        Spacer(modifier = Modifier.height(Dimens.SmallPadding))

                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.Bottom
                        ) {
                            var totalPrice = item.price

                            if (item.quantity != 1) {
                                totalPrice = item.price * item.quantity
                            }

                            Text(
                                text = "$ $totalPrice",
                            )

                            QuantitySelector(
                                currentQuantity = item.quantity,
                                onQuantityChanged = { newQuantity ->
                                    event(CartUiEvent.UpdateQuantity(item.idItems, newQuantity))
                                },
                                minQuantity = 1
                            )
                        }
                    }


                }
            }
        }
    }


}