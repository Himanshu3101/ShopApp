package com.example.shopapp.features.productDetail.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
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
import androidx.compose.ui.text.style.TextOverflow
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

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            ProductDetailLayout(state, navController)
        }

        AddRemoveItem(modifier = Modifier.align(Alignment.Center)) // Aligns the center of the button to the center of the parent Box
    }

}

@Composable
fun AddRemoveItem(modifier: Modifier) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(Dimens.MediumCornerRadius))
            .padding(bottom = Dimens.SmallPadding / 9) // Move it up by half its height to sit on the dividing line
            .background(colorResource(R.color.blue))
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
            onClick = { /*if (quantity > 1) quantity--*/ },
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
            text = "0",
            modifier = Modifier
                .padding(Dimens.MediumPadding)
        )

        // Add Button
        IconButton(
            onClick = { /*if (quantity > 1) quantity--*/ },
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
fun ProductDetailLayout(state: ProductDetailState, navController: NavHostController) {
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

            OptionImage(state)

        }

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxSize().padding(bottom = Dimens.SmallPadding)
                .background(colorResource(R.color.blue_light))
        ) {
            ProductDetailData(state)
        }
    }
}

@Composable
fun OptionImage(state: ProductDetailState) {
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
                    )
                }
            }
        }
    }

}

@Composable
fun ProductDetailData(state: ProductDetailState) {
    state.items?.let { item ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(Dimens.LargePadding)
        ) {
            Text(
                text = state.items.title.toString(),
                fontWeight = Bold,
                fontSize = Dimens.LargeText
            )

            Spacer(modifier = Modifier.height(Dimens.MediumPadding))

            Text(
                text = "Product Details",
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
                    .weight(1f, fill = false)
            )
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
fun MainImage(state: ProductDetailState, modifier: Modifier) {
    state.items?.imageUrl?.getOrNull(0)?.let { mainImageUrl ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(Dimens.MediumPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(mainImageUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = "Product Details",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .fillMaxSize()
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