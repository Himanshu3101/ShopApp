package com.example.shopapp.features.productDetail.presentation

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowColumn
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddShoppingCart
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.shopapp.R
import com.example.shopapp.core.util.Constants
import com.example.shopapp.features.common.components.ButtonBox
import com.example.shopapp.features.productDetail.presentation.event.ProductDetailUiEvent
import com.example.shopapp.features.productDetail.presentation.state.ProductDetailState
import com.example.shopapp.features.productDetail.presentation.state.ProductDetailUiModel
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
    val context = LocalContext.current
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            ProductDetailLayout(state, navController, event,context)
        }

        AddRemoveItem(modifier = Modifier.align(Alignment.Center), state, event, context) // Aligns the center of the button to the center of the parent Box
    }

}
@Composable
fun ProductDetailLayout(
    state: ProductDetailState,
    navController: NavHostController,
    event: (ProductDetailUiEvent) -> Unit,
    context: Context,
    ) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxSize()
                .background(colorResource(R.color.white))
        ) {

            MainImage(
                state,
                modifier = Modifier.align(Alignment.Center)
            )
            DetailToolBar(navController)

            OptionImage(state, event)

        }

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxSize()
                .background(colorResource(R.color.blue_light))
        ) {
            ProductDetailData(state, event, context)
        }
    }
}


@Composable
fun MainImage(state: ProductDetailState, modifier: Modifier) {
    val imageUrlToDisplay = state.selectedMainImageUrl

    if(imageUrlToDisplay != null){
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(Dimens.MediumPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(imageUrlToDisplay)
                    .crossfade(true)
                    .build(),
                contentDescription = "Product Details",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .fillMaxSize()
            )
        }

    }/*else{
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(Dimens.MediumPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            if (state.isLoading) {
                CircularProgressIndicator()
                Spacer(Modifier.height(Dimens.SmallPadding))
                Text("Loading image...", fontSize = Dimens.SmallText)
            } else if (state.errorMsg != null) {
                Text("Error: ${state.errorMsg}", color = Color.Red, fontSize = Dimens.SmallText)
            } else {
                Text("No images available", fontSize = Dimens.SmallText)
            }
        }
    }*/
}

@Composable
fun AddRemoveItem(
    modifier: Modifier,
    state: ProductDetailState,
    event: (ProductDetailUiEvent) -> Unit,
    context: Context
) {

    Row(
        modifier = modifier
            .clip(RoundedCornerShape(Dimens.MediumCornerRadius))
            .padding(bottom = Dimens.SmallPadding / 9) // Move it up by half its height to sit on the dividing line
            .background(colorResource(R.color.blue))
            .height(Dimens.MediumBoxHeight)
            .border(
                Dimens.SmallBorderWidth,
                colorResource(R.color.blue),
                RoundedCornerShape(Dimens.LargeCornerRadius)
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(Dimens.SmallPadding),
    ) {

        //Subtract
        IconButton(
            onClick = {
                if(state.items!!.cartQuantity==0){
                    Constants.showToast(context = context, message = "Can't be less 0")
                }else{
                    event(ProductDetailUiEvent.setQuantity(state.items.cartQuantity - 1))
                }
            },
            modifier = Modifier
                .padding(Dimens.MediumPadding)
                .size(Dimens.IconSizeMedium)
                .clip(CircleShape) // Make it circular
                .background(
                    colorResource(R.color.white),
                    shape = CircleShape
                ), // Example background
        ) {
            Icon(
                imageVector = Icons.Default.Remove,
                contentDescription = "Subtract Quantity",
                tint = MaterialTheme.colorScheme.primary
            )
        }

        Text(
            text = state.items?.cartQuantity.toString(),
            modifier = Modifier
                .padding(Dimens.MediumPadding)
        )

        // Add Button
        IconButton(
            onClick = {
                event(ProductDetailUiEvent.setQuantity(state.items?.cartQuantity?.plus(1) ?: 0))
            },
            modifier = Modifier
                .padding(Dimens.MediumPadding)
                .size(Dimens.IconSizeMedium)
                .clip(CircleShape) // Make it circular
                .background(
                    colorResource(R.color.white),
                    shape = CircleShape
                ), // Example background
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Add Quantity",
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
fun OptionImage(state: ProductDetailState, event: (ProductDetailUiEvent) -> Unit,) {
    state.items?.imageUrl?.let { imageUrls ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = Dimens.ExtraLargePadding, end = Dimens.SmallPadding),
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.Center
        ) {
            FlowColumn {
                imageUrls.forEach { columnItem ->
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(columnItem)
                            .crossfade(true)
                            .build(),
                        contentDescription = "Image Varient",
                        modifier = Modifier
                            .height(Dimens.sizeVariantProductItem)
                            .padding(Dimens.SmallBorderWidth)
                            .clickable{
                                event(ProductDetailUiEvent.setMainImage(columnItem))
                            }
                    )
                }
            }
        }
    }

}

@Composable
fun ProductDetailData(
    state: ProductDetailState,
    event: (ProductDetailUiEvent) -> Unit,
    context: Context
) {
    state.items?.let { item ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(Dimens.SmallPadding)
        ) {
            Text(
                text = state.items.title,
                modifier = Modifier.padding(start = Dimens.SmallPadding, top = Dimens.MediumPadding),
                fontWeight = Bold,
                fontSize = Dimens.LargeText
            )

            Spacer(modifier = Modifier.height(Dimens.MediumPadding))

            Text(
                text = "Product Details",
                modifier = Modifier.padding(start = Dimens.SmallPadding),
                fontWeight = Bold,
                fontSize = Dimens.MediumText
            )

            Spacer(modifier = Modifier.height(Dimens.MediumPadding))

            Text(
                text = item.description,
                fontSize = Dimens.MediumText,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
                    .padding(start = Dimens.SmallPadding)
                    .weight(1f/*, fill = false*/)
            )

            Spacer(modifier = Modifier.height(Dimens.MediumPadding))

            PriceAndCart(item, event, state, context)
        }
    } ?: run {
        // Show loading or error state if items is null
        Text(
            text = "Loading Products Details",
            modifier = Modifier
                .fillMaxSize()
                .padding(top = Dimens.ExtraLargePadding,start = Dimens.MediumPadding, end = Dimens.MediumPadding)
        )
    }
}

@Composable
fun PriceAndCart(
    item: ProductDetailUiModel,
    event: (ProductDetailUiEvent) -> Unit,
    state: ProductDetailState,
    context: Context
) {

//    var rememberQuantity = rememberSaveable{mutableIntStateOf(state.items?.cartQuantity?:0)}



    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(Dimens.SM_BoxHeight)
            .padding(horizontal = Dimens.SmallPadding),
        contentAlignment = Alignment.CenterStart
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    color = colorResource(R.color.blue),
                    shape = RoundedCornerShape(Dimens.LargeCornerRadius)
                )
        ) {
            Text(
                text = "$ ${item.price}",
                fontWeight = Bold,
                fontSize = Dimens.MediumText,
                color = colorResource(R.color.black),
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .align(Alignment.CenterStart) // Align text within its parent Box
                    .padding(start = Dimens.ExtraLargePadding) // Padding for the text itself
            )
        }

        //Button Add to Cart
        Box(
            modifier = Modifier.align(Alignment.CenterEnd)// <-- KEY: This aligns the button
        ) {
            ButtonBox(
                containerColor = colorResource(R.color.blue2),
                borderColor = colorResource(R.color.blue2),
                text = "Add to Cart",
                icon = Icons.Default.AddShoppingCart,
                onClick = {
                    if(state.items?.cartQuantity != 0){


                        event(ProductDetailUiEvent.onAddToCart)


                    }else{
                        Constants.showToast(context = context, message = "Can't be less 0")
                    }
                }
            )
        }
    }
}

@Composable
fun DetailToolBar(navController: NavHostController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(all = Dimens.MediumPadding)
    ) {
        //Back
        IconButton(onClick = {
            navController.navigateUp()
        }) {
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
            }) {
                Image(
                    painterResource(R.drawable.fav),
                    contentDescription = "back_productDetails"
                )
            }

            //Share
            IconButton(onClick = {
//                        navController.navigateUp()
            }) {
                Image(
                    painterResource(R.drawable.share),
                    contentDescription = "back_productDetails"
                )
            }
        }
    }
}