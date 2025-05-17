package com.example.shopapp.features.dashboard.presentation.screen.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
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
import androidx.compose.ui.text.font.FontWeight
import com.example.shopapp.R
import com.example.shopapp.ui.theme.Dimens


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