package com.example.shopapp.features.productDetail.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.shopapp.R
import com.example.shopapp.features.productDetail.presentation.event.ProductDetailUiEvent
import com.example.shopapp.features.productDetail.presentation.state.ProductDetailState
import com.example.shopapp.ui.theme.Dimens

@Preview
@Composable
fun Prev_ProductDetailUI() {
    ProductDetailUI(
        navController = rememberNavController(),
        state = ProductDetailState(),
        event = {}
    )
}


@Composable
fun ProductDetailUI(
    navController: NavHostController,
    state: ProductDetailState,
    event: (ProductDetailUiEvent) -> Unit
) {
    Column(
        modifier = Modifier

            .fillMaxSize()
    ){

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxSize()
                .background(colorResource(R.color.white))
        ){

            MainImage(
                state,
                modifier = Modifier.align(Alignment.Center)
            )

            DetailToolBar(navController)
        }

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxSize()
                .background(colorResource(R.color.blue_light))
        ){
            ProductDetailData(state)
        }
    }
}

@Composable
fun ProductDetailData(state: ProductDetailState) {
    Column{
        Text(
            text = state.items?.title.toString(),
            fontWeight = Bold,
            fontSize = Dimens.LargeText,
            modifier = Modifier.padding(start = Dimens.LargePadding, top = Dimens.LargePadding)
        )

        Spacer(modifier = Modifier.height(Dimens.MediumPadding))

        Text(
            text = "Product Details",
            fontWeight = Bold,
            fontSize = Dimens.MediumText,
            modifier = Modifier.padding(start = Dimens.MediumPadding, top = Dimens.MediumPadding)
        )

        Spacer(modifier = Modifier.height(Dimens.MediumPadding))

        Text(
            text = state.items?.description.toString(),
            fontSize = Dimens.MediumText,
            maxLines = 9,
            modifier = Modifier
                .padding(start = Dimens.MediumPadding, top = Dimens.MediumPadding)
                .fillMaxSize()
        )
    }
}

@Composable
fun MainImage(state: ProductDetailState, modifier: Modifier) {
    Column(
        modifier = modifier
            .padding(Dimens.MediumPadding),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(state.items?.imageUrl[0])
                .crossfade(true)
                .build(),
            contentDescription ="Product Details",
            modifier = Modifier
                .fillMaxSize()
        )
    }
}

@Composable
fun DetailToolBar(navController: NavHostController) {
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .padding(all = Dimens.MediumPadding)
    ){
        //Back
        IconButton(onClick = {
            navController.navigateUp()
        }){
            Image(
                painterResource(R.drawable.back),
                contentDescription = "back_productDetails"
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = Dimens.MediumPadding),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            //Save
            IconButton(onClick = {
//                        navController.navigateUp()
            }){
                Image(
                    painterResource(R.drawable.fav),
                    contentDescription = "back_productDetails"
                )
            }

            //Share
            IconButton(onClick = {
//                        navController.navigateUp()
            }){
                Image(
                    painterResource(R.drawable.share),
                    contentDescription = "back_productDetails"
                )
            }
        }
    }
}