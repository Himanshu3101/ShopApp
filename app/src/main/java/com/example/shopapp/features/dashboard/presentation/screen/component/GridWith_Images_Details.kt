package com.example.shopapp.features.dashboard.presentation.screen.component

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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.shopapp.R
import com.example.shopapp.core.util.Constants.deviceSize
import com.example.shopapp.features.dashboard.presentation.screen.state.ItemData
import com.example.shopapp.ui.theme.Dimens

@Preview
@Composable
fun Prev_GridWith_Images_Details() {
    val data = listOf(
        ItemData(
            imageUrl = listOf(
                "https://res.cloudinary.com/dkikc5ywq/image/upload/v1746098536/1_1_db56nv.png",
                "https://res.cloudinary.com/dkikc5ywq/image/upload/v1746098532/1_4_vdstgc.jpg"
            ),
            price = 89,
            rating = 4.5,
            title = "Product 1",
            categoryId = 1
        ),
        ItemData(
            imageUrl = listOf(
                "https://res.cloudinary.com/dkikc5ywq/image/upload/v1746098532/1_4_vdstgc.jpg"
            ),
            price = 120,
            rating = 4.8,
            title = "Product 2",
            categoryId = 2
        )
    )
    GridWith_Images_Details(itemData = data)
}

@Composable
fun GridWith_Images_Details(itemData: List<ItemData>) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .fillMaxWidth()
            .height(deviceSize() * 0.85f)
            .padding(Dimens.SmallPadding),
        verticalArrangement = Arrangement.spacedBy(Dimens.SmallSpacerHeight),
        horizontalArrangement = Arrangement.spacedBy(Dimens.SmallSpacerHeight),
    ) {

        val size = itemData

        items(itemData.size) { index ->
            ImageWithDetails(itemDetails = itemData)
        }
    }
}

@Composable
fun ImageWithDetails(itemDetails: List<ItemData>) {

    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Box(modifier = Modifier.size(Dimens.BannerImageSize)) {

            //Background Box
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        colorResource(R.color.white),
                        RoundedCornerShape(Dimens.SmallCornerRadius)
                    )
            )

            //OverlayBox
            Box(
                modifier = Modifier
                    .height(Dimens.LargeBoxHeight)
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
//                .padding(top = Dimens.MediumBoxHeight)
                    .background(
                        colorResource(R.color.blue_light),
                        RoundedCornerShape(Dimens.SmallCornerRadius)
                    )
            ) {
                Downbar(
                    modifier = Modifier.align(Alignment.BottomCenter),
                    itemDetails = itemDetails
                )
            }


            Image_Title(
                modifier = Modifier.align(Alignment.TopCenter),
                itemData = itemDetails
            )
        }
    }
}

@Composable
fun Image_Title(modifier: Modifier, itemData: ItemData) {
    Column(
        modifier = modifier
            .padding(Dimens.SmallPadding),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(itemData.imageUrl.get(0))
                .crossfade(true)
                .build(),
            contentDescription = "Item Image",
            modifier = Modifier
                .height(Dimens.sizeProductItem)
                .clip(RoundedCornerShape(Dimens.SmallPadding))
        )
        Spacer(modifier = Modifier.height(Dimens.SmallPadding))

        Text(
            text = itemData.title,
            textAlign = TextAlign.Center,
            maxLines = 2,
            fontSize = Dimens.verySmallText,
            color = Color.Black
        )
    }
}

@Composable
fun Downbar(modifier: Modifier, itemDetails: ItemData) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(all = Dimens.SmallPadding),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = "$${itemDetails.price}", color = Color.Black, fontWeight = FontWeight.Bold)

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "${itemDetails.rating}", color = Color.Black)

            Spacer(modifier = Modifier.height(Dimens.SmallPadding))

            Image(
                painter = painterResource(id = R.drawable.star),
                contentDescription = "Star"
            )
        }
    }

}





