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
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.shopapp.R
import com.example.shopapp.core.util.Constants.deviceSize
import com.example.shopapp.ui.theme.Dimens

@Preview
@Composable
fun Prev_GridWith_Images_Details() {

//    val dummyImageUrls = List(10) { "https://via.placeholder.com/150" }

    val dummyImageUrls = listOf(
        "https://picsum.photos/id/1015/600/400",
        "https://picsum.photos/id/1020/600/400",
        "https://picsum.photos/id/1024/600/400",
        "https://picsum.photos/id/1035/600/400",
        "https://picsum.photos/id/1043/600/400"
    )

    GridWith_Images_Details(dummyImageUrls)
}

@Composable
fun GridWith_Images_Details(dummyImageUrls: List<String>) {


    LazyVerticalGrid(
        columns = GridCells.Fixed(2), // Define the number of columns
        modifier = Modifier
            .fillMaxWidth()
            .height(deviceSize() * 0.85f)
            .padding(Dimens.SmallPadding),
        verticalArrangement = Arrangement.spacedBy(Dimens.SmallSpacerHeight),
        horizontalArrangement = Arrangement.spacedBy(Dimens.SmallSpacerHeight),
    ) {
        items(dummyImageUrls.size) { index ->
            ImageWithDetails(imageUrls = dummyImageUrls[index])
        }
    }
}

@Composable
fun ImageWithDetails(imageUrls: String) {
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
            ){
                Downbar(modifier = Modifier.align(Alignment.BottomCenter))
            }


            Image_Title(
                modifier = Modifier.align(Alignment.TopCenter),
                imageUrls = imageUrls
            )
        }
    }
}

@Composable
fun Image_Title(imageUrls: String, modifier: Modifier) {
    Column(
        modifier = modifier
            .padding(Dimens.SmallPadding),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(imageUrls)
                .crossfade(true)
                .build(),
            contentDescription = "Item Image",
            modifier = Modifier
                .height(Dimens.sizeProductItem)
                .clip(RoundedCornerShape(Dimens.SmallPadding))
        )
        Spacer(modifier = Modifier.height(Dimens.SmallPadding))
        Text(
            text = "Sample Item Title",
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
    }
}

@Composable
fun Downbar(modifier: Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(all = Dimens.SmallPadding),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = "$ 95.0", color = Color.Black, fontWeight = FontWeight.Bold)

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "4.6", color = Color.Black)

            Spacer(modifier = Modifier.height(Dimens.SmallPadding))

            Image(
                painter = painterResource(id = R.drawable.star),
                contentDescription = "Star"
            )
        }
    }

}





