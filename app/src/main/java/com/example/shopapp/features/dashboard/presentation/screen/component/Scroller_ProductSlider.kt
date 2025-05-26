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
import com.example.shopapp.features.dashboard.presentation.screen.event.DashboardUiEvent
import com.example.shopapp.features.dashboard.presentation.screen.state.CategoryDetails
import com.example.shopapp.ui.components.ButtonBox

@Preview
@Composable
fun Prev_Scroller_ProductSlider() {
    val productList = listOf(
        CategoryDetails(id = 1,"Shampoo"),
        CategoryDetails(id = 2,"Skin")
    )
    Scroller_ProductSlider(productList, event ={})
}

@Composable
fun Scroller_ProductSlider(productList: List<CategoryDetails>, event: (DashboardUiEvent) -> Unit) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth(),
    ) {
        items(productList) { list ->

            ButtonBox(
                modifier = Modifier
                    .wrapContentWidth(),
                text = list.title,
                textColor = colorResource(id = R.color.black),
                borderColor = colorResource(id = R.color.white),
            ) {
                event(DashboardUiEvent.SetProductId(list.id.toString()))
            }
        }


    }
}