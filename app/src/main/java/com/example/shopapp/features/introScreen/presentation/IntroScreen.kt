package com.example.shopapp.features.introScreen.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.shopapp.R
import com.example.shopapp.features.common.components.ButtonBox
import com.example.shopapp.ui.navigation.Routes
import com.example.shopapp.ui.theme.Dimens

@Preview
@Composable
fun PrevIntro() {
    IntroScreen(navController = rememberNavController())
}

@Composable
fun IntroScreen(navController: NavHostController) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {

        Image(
            painterResource(id = R.drawable.intro_pic),
            contentDescription = "introPic",
            contentScale = ContentScale.Crop,
            modifier = Modifier.matchParentSize()
        )

        Column(
            modifier = Modifier
                .align(alignment = Alignment.TopStart)

        ) {
            Text(
                text = stringResource(R.string.splash_title),
                style = MaterialTheme.typography.headlineLarge.copy(
                    lineHeight = Dimens.LargeLineSpacing
                ),
                color = colorResource(R.color.white),
                fontSize = Dimens.HeadingText,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(
                    start = Dimens.LargePadding,
                    top = Dimens.ExtraLargePadding
                )
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = Dimens.SmallPadding),
                horizontalArrangement = Arrangement.Center
            ) {
                ButtonBox(
                    text = "Let's Get Started",
                    fraction = 0.6f
                ) {
                    navController.navigate(Routes.Dashboard.route)
                }
            }
        }

    }
}