package com.example.shopapp.features.dashboard.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.shopapp.R
import com.example.shopapp.features.dashboard.presentation.screen.component.GridWith_Images_Details
import com.example.shopapp.features.dashboard.presentation.screen.component.Header_UserScrn
import com.example.shopapp.features.dashboard.presentation.screen.component.Scroller_ProductSlider
import com.example.shopapp.features.dashboard.presentation.screen.component.Search_UserScrn
import com.example.shopapp.features.dashboard.presentation.screen.event.ev_dashboard
import com.example.shopapp.features.dashboard.presentation.screen.state.st_Dashboard
import com.example.shopapp.ui.components.ImagePager
import com.example.shopapp.ui.theme.Dimens


@Preview(/*showBackground = true*/)
@Composable
fun PrevDash() {
    Dashboard(
        navHostController = rememberNavController(),
        event = {},
        state = st_Dashboard()
    )
}

@Composable
fun Dashboard(
    navHostController: NavHostController,
    event: (ev_dashboard) -> Unit,
    state: st_Dashboard
) {

    val isInitOnce = rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(key1 = true) {
        if (!isInitOnce.value) {
            isInitOnce.value = true
            event(ev_dashboard.InitDashboard)
        }

    }


    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(
                top = Dimens.ExtraLargePadding,
                start = Dimens.SmallPadding,
                end = Dimens.SmallPadding
            ),
        verticalArrangement = Arrangement.spacedBy(Dimens.SmallSpacerHeight)
    ) {

        item {
            Header_UserScrn()
        }

        item {
            Search_UserScrn()
        }


        item {
            if (state.bannerUrls.isNotEmpty()) {
                ImagePager(imageUrls = state.bannerUrls)
            }
        }


//        Product
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(Dimens.SmallPadding),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = stringResource(R.string.product),
                    style = MaterialTheme.typography.titleMedium
                )

                Text(
                    text = stringResource(R.string.see_all),
                    onTextLayout = {}, // required parameter
                    modifier = Modifier.clickable { },
                    style = TextStyle(color = colorResource(id = R.color.blue)),
//                onClick = {}
                )

            }
        }

        item {
            if (state.categoryList.isNotEmpty()) {
                Scroller_ProductSlider(productList = state.categoryList, event)
            }

        }

//        Popular Products
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(Dimens.SmallPadding),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = stringResource(R.string.popular_product),
                    style = MaterialTheme.typography.titleMedium
                )

                Text(
                    text = stringResource(R.string.see_all),
                    onTextLayout = {}, // required parameter
                    modifier = Modifier.clickable { },
                    style = TextStyle(color = colorResource(id = R.color.blue)),
//                onClick = {}
                )

            }
        }

        item {

            if (!isInitOnce.value) {
                if (state.setProductId.toString() == "0") {
//                    GridWith_Images_Details(state.itemsState.get())
                } else {
                    val id = state.categoryList[0].Pid
                    GridWith_Images_Details(state.itemsState[id])
                }
                /* Log.e("Dashboard", "Showing products $isInitOnce")
                 GridWith_Images_Details(state)*/
            }

        }
    }

}


