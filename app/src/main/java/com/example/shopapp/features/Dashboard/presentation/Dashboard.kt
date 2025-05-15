package com.example.shopapp.features.Dashboard.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.shopapp.R
import com.example.shopapp.features.Dashboard.component.GridWith_Images_Details
import com.example.shopapp.features.Dashboard.component.Scroller_ProductSlider
import com.example.shopapp.features.common.ImagePager
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
//        Spacer(modifier = Modifier.height(Dimens.MediumSpacerHeight))
        item{
            Search_UserScrn()
        }
//        Spacer(modifier = Modifier.height(Dimens.SmallSpacerHeight))

        val imageList = listOf(
            "https://via.placeholder.com/150",
            "https://picsum.photos/id/237/200/300",
            "https://picsum.photos/id/238/200/300"
        )
        item{
            ImagePager(imageList)
        }

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

@Composable
fun Header_UserScrn() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(Dimens.SmallPadding),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painterResource(R.drawable.profile),
            contentDescription = "userPic",
            modifier = Modifier.weight(1f)
        )

        Column(
            modifier = Modifier.weight(3f)
        ) {
            Text(
                text = stringResource(R.string.welcomeBack),
                style = MaterialTheme.typography.titleSmall
            )
            Text(
                text = stringResource(R.string.userName),
                style = MaterialTheme.typography.titleMedium
            )
        }

        Image(
            painterResource(R.drawable.bell_icon),
            contentDescription = "userPic",
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun Search_UserScrn() {

    var searchText by remember { mutableStateOf("") } // State to hold the search text

    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Box(
            modifier = Modifier
                .weight(4f)
                .fillMaxWidth()
                .border(Dimens.VerySmallBorderWidth, colorResource(id = R.color.lightGrey), RoundedCornerShape(Dimens.LargeCornerRadius),)
                .clip(RoundedCornerShape(Dimens.LargeCornerRadius)),
            contentAlignment = Alignment.TopStart

        ) {

            Row(
                modifier = Modifier
                    .padding(Dimens.SmallPadding)
                    .fillMaxWidth(),
                horizontalArrangement =Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {


                TextField(
                    value = searchText,
                    onValueChange = {searchText = it},
                    leadingIcon = {
                        Icon(
                            painterResource(R.drawable.search_icon),
                            contentDescription = "",
                            modifier = Modifier
                                .size(Dimens.IconSizeMedium),
                        )
                    },
                    placeholder = {
                        Text(
                            text = "Search...",
                            fontWeight = FontWeight.Medium,
                            fontSize = Dimens.SmallText,
                            color = Color.Gray // Set color to differentiate from text
                        )
                    },
                    singleLine = true,
                    modifier = Modifier
                        .weight(5f),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent, // Transparent background when focused
                        cursorColor = Color.Black, // Color of the cursor
                        focusedTextColor = Color.Black, // Color of the text when focused
                        focusedIndicatorColor = Color.Transparent, // Remove the indicator line
                        unfocusedContainerColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                    )
                )

                Image(
                    painterResource(R.drawable.microphone),
                    contentDescription = "microphoneIcon",
                    modifier = Modifier.weight(1f)
                )
            }
        }

        Image(
            painterResource(R.drawable.settings_icon),
            contentDescription = "settingIcon",
            modifier = Modifier.weight(1f)
        )
    }
}