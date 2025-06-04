package com.example.shopapp.ui.common.components

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.shopapp.R
import com.example.shopapp.core.util.Constants.Shoplog
import com.example.shopapp.features.dashboard.presentation.screen.state.ItemData
import com.example.shopapp.ui.common.interfaces.DisplayableItem
import com.example.shopapp.ui.theme.Dimens

@Preview
@Composable
fun Prev_GridWith_Images_Details() {
    val data = listOf(
        ItemData(
            imageUrl = listOf(
                "https://images.unsplash.com/photo-1506744038136-46273834b3fb",
                "https://images.unsplash.com/photo-1544005313-94ddf0286df2"
            ),
            price = 89,
            rating = 4.5,
            title = "Product 1",
            categoryId = "1",
            showRecommended = true
        ),
        ItemData(
            imageUrl = listOf(
                "https://images.unsplash.com/photo-1544005313-94ddf0286df2"
            ),
            price = 120,
            rating = 4.8,
            title = "Product 2",
            categoryId = "2",
            showRecommended = true
        )
    )
    GridWith_Images_Details(itemData = data)
}

@Composable
fun GridWith_Images_Details(itemData: List<DisplayableItem>) {
    Log.e(Shoplog, "itemData Size ${itemData.size}")
    FlowRow(
        modifier = Modifier
            .padding(Dimens.SmallPadding).background(colorResource(R.color.white))
            .testTag(stringResource(R.string.gridImageTest)),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        maxItemsInEachRow = 2 // Optional
    ) {
        itemData.chunked(2).forEach { rowItems ->
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                rowItems.forEach { item ->
                    Box(modifier = Modifier.weight(1f)) {
                        if (item.showRecommended) {
                            ImageWithDetails(itemDetails = item)
                        }
                    }
                }
            }

            if (rowItems.size < 2) {
                Spacer(modifier = Modifier.weight(1f))
            }
        }
    }
}

@Composable
fun ImageWithDetails(itemDetails: DisplayableItem) {
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
fun Image_Title(modifier: Modifier, itemData: DisplayableItem) {
    Column(
        modifier = modifier
            .padding(Dimens.SmallPadding),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(itemData.imageUrl[0])
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
fun Downbar(modifier: Modifier, itemDetails: DisplayableItem) {
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





