package com.example.shopapp.features.Dashboard.component


import android.graphics.Color
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.shopapp.R
import com.example.shopapp.ui.components.common.ButtonBox
import com.example.shopapp.ui.theme.Dimens

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