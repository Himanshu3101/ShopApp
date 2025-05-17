package com.example.shopapp.features.dashboard.presentation.screen.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.example.shopapp.R
import com.example.shopapp.ui.theme.Dimens

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