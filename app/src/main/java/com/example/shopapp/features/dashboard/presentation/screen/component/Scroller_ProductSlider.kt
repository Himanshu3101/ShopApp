package com.example.shopapp.features.dashboard.presentation.screen.component


import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.shopapp.R
import com.example.shopapp.ui.components.ButtonBox

@Preview
@Composable
fun Prev_Scroller_ProductSlider() {
    val productList = listOf(
        "Shampoo",
        "Skin",
        "Facial",
        "Beared",
        "Bearedo"
    )
    Scroller_ProductSlider(productList)
}

@Composable
fun Scroller_ProductSlider(productList: List<String>) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth(),
    ) {
        items(productList) { text ->

            ButtonBox(
                modifier = Modifier
                    .wrapContentWidth(),
                text = text,
                textColor = colorResource(id = R.color.black),
                borderColor = colorResource(id = R.color.white),
            ) {

            }
        }


    }
}