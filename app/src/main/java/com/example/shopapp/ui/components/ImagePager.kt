package com.example.shopapp.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.SubcomposeAsyncImage
import com.example.shopapp.R
import com.example.shopapp.ui.theme.Dimens


@Preview
@Composable
fun Prev_ImagePager() {
    val list = listOf(
        "ABC",
        "DEF",
        "ABC",
        "DEF",
    )
    ImagePager(list)
}

@Composable
fun ImagePager(imageUrls: List<String>) {

    val pagerState = rememberPagerState(pageCount = { imageUrls.size })

    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.height(Dimens.ExtraLargeBoxHeight),
            userScrollEnabled = true,
            ) { page ->

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(Dimens.SmallPadding)
                    .clip(RoundedCornerShape(Dimens.SmallPadding)),
                contentAlignment = Alignment.Center
            ) {

                SubcomposeAsyncImage(
                    model = imageUrls[page],
                    contentDescription = "Image $page",
                    contentScale = ContentScale.Crop,
                    loading = {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(Dimens.MediumPadding),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    },
                    error = {
                        Image(
                            painter = painterResource(id = R.drawable.dummy_image),
                            contentDescription = "Dummy Image",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(Dimens.LargeBoxHeight),
                )
            }
        }

        AnimatedDotsIndicator(
            totalDots = imageUrls.size,
            selectedIndex = pagerState.currentPage
        )
    }
}