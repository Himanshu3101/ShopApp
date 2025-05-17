package com.example.shopapp.features.dashboard.presentation.screen.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
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

    val dummyImageUrls  = List(10){"https://via.placeholder.com/150"}
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
        items (dummyImageUrls.size){index->
            ImageWithDetails(imageUrls = dummyImageUrls[index])
        }
    }
}

@Composable
fun ImageWithDetails(imageUrls: String) {
    Column (
        modifier = Modifier
            .fillMaxWidth()
            .background(colorResource(R.color.blue_light), RoundedCornerShape(Dimens.SmallCornerRadius))
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
                .height(Dimens.BannerImageSize)
                .fillMaxWidth()
                .clip(RoundedCornerShape(Dimens.SmallPadding))
        )

        Spacer(modifier = Modifier.height(Dimens.SmallPadding))

        Button(
            onClick = { /* Handle click */ },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Open", fontWeight = FontWeight.Bold)
        }
    }
}