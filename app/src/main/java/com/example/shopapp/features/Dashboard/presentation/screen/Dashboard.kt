package com.example.shopapp.features.Dashboard.presentation.screen

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.shopapp.R
import com.example.shopapp.features.Dashboard.presentation.screen.component.GridWith_Images_Details
import com.example.shopapp.features.Dashboard.presentation.screen.component.Header_UserScrn
import com.example.shopapp.features.Dashboard.presentation.screen.component.Scroller_ProductSlider
import com.example.shopapp.features.Dashboard.presentation.screen.component.Search_UserScrn
import com.example.shopapp.ui.components.ImagePager
import com.example.shopapp.ui.theme.Dimens


@Preview(/*showBackground = true*/)
@Composable
fun PrevDash() {
    Dashboard(navHostController = rememberNavController())
}

@Composable
fun Dashboard(navHostController: NavHostController) {

    LazyColumn (
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(Dimens.SmallPadding),
        verticalArrangement = Arrangement.spacedBy(Dimens.SmallSpacerHeight)
    ) {

        item{
            Header_UserScrn()
        }

        item{
            Search_UserScrn()
        }

        val imageList = listOf(
            "https://via.placeholder.com/150",
            "https://picsum.photos/id/237/200/300",
            "https://picsum.photos/id/238/200/300"
        )
        item{
            ImagePager(imageList)
        }


//        Product
        item{
            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(Dimens.SmallPadding),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ){

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

        val productList = listOf(
            "Shampoo",
            "Skin",
            "Facial",
            "Beared",
            "Bearedo"
        )
        item {
            Scroller_ProductSlider(productList)
        }

//        Popular Products
        item{
            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(Dimens.SmallPadding),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ){

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

        val dummyImageUrls  = List(20){"https://via.placeholder.com/150"}
        item{
            GridWith_Images_Details(dummyImageUrls )
        }
    }

}


