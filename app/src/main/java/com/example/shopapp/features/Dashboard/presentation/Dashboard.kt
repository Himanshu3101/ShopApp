package com.example.shopapp.features.Dashboard.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.provider.FontsContractCompat.Columns
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.shopapp.R
import com.example.shopapp.ui.theme.Dimens


@Preview(showBackground = true)
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
            ){
                Text(
                    text = stringResource(R.string.welcomeBack),
                    style =MaterialTheme.typography.titleSmall
                )
                Text(
                    text = stringResource(R.string.userName),
                    style =MaterialTheme.typography.titleMedium
                )
            }

            Image(
                painterResource(R.drawable.bell_icon),
                contentDescription = "userPic",
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(Dimens.SmallSpacerHeight))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = Dimens.SmallPadding,
                    end = Dimens.SmallPadding
                ),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Box (){

            }

            Image(
                painterResource(R.drawable.settings_icon),
                contentDescription = "userPic",
                modifier = Modifier.weight(1f)
            )
        }
    }

}