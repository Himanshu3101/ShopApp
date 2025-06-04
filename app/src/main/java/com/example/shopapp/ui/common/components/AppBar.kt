package com.example.shopapp.ui.common.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.example.shopapp.R
import com.example.shopapp.ui.theme.Dimens

@Preview
@Composable
fun Prev_AppBar() {
    AppBar(title = "hello", onBackClick = {})
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(
    title: String,
    onBackClick: () -> Unit
) {
    TopAppBar(
        modifier = Modifier
            .fillMaxWidth()
            .testTag(stringResource(R.string.app_bar)),
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = colorResource(R.color.white),
            actionIconContentColor = colorResource(R.color.darkGrey),
            navigationIconContentColor = colorResource(R.color.darkGrey)
        ),

        title = {
            Text(
                modifier = Modifier.fillMaxWidth().padding(end = Dimens.LargePadding),
                text = title,
                color = colorResource(R.color.black),
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Normal,
                fontSize = Dimens.LargeText,
                maxLines = 1,
                textAlign = TextAlign.Center,
                overflow = TextOverflow.Ellipsis
            )
        },

        navigationIcon ={
            IconButton(onClick = onBackClick){
                Icon(painterResource(id = R.drawable.back), contentDescription = null)
            }
        }
    )
}