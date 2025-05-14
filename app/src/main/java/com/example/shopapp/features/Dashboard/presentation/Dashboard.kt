package com.example.shopapp.features.Dashboard.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.min
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.shopapp.R
import com.example.shopapp.ui.theme.Dimens


@Preview(/*showBackground = true*/)
@Composable
fun PrevDash() {
    Dashboard(navHostController = rememberNavController())
}

@Composable
fun Dashboard(navHostController: NavHostController) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(Dimens.SmallPadding)
    ) {

        Dashboard_1()

        Spacer(modifier = Modifier.height(Dimens.MediumSpacerHeight))

        Dashboard_2()

        Spacer(modifier = Modifier.height(Dimens.MediumSpacerHeight))



    }

}


@Composable
fun Dashboard_1() {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
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
fun Dashboard_2() {

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