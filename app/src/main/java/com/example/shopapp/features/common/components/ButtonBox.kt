package com.example.shopapp.features.common.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import com.example.shopapp.R
import com.example.shopapp.ui.theme.Dimens

@Preview
@Composable
fun ButtonBoxPrev(){
    ButtonBox(
        text = "Next"
    ) { }
}

@Composable
fun ButtonBox(
    modifier: Modifier = Modifier,
    text: String,
    boxHeight: Dp = Dimens.SM_BoxHeight,
    padding: Dp = Dimens.SmallPadding,
    borderColor: Color =  colorResource(id = R.color.blue),
    containerColor: Color =  colorResource(id = R.color.blue),
    textColor: Color = colorResource(id = R.color.white),
    fontSize: TextUnit = Dimens.MediumText,
    fraction: Float = 0.6f,
    icon: ImageVector? = null,
    onClick: () -> Unit
){
    Box(
        modifier = modifier
//            .padding(padding)
            .height(boxHeight)
            .border(Dimens.SmallBorderWidth, borderColor, RoundedCornerShape(Dimens.LargeCornerRadius))
            .clickable {onClick()}
            .fillMaxWidth(fraction)
            .clip(RoundedCornerShape(Dimens.LargeCornerRadius))
            .background(containerColor),
        contentAlignment = Alignment.Center
    ){

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (icon != null) {
                Icon(
                    imageVector = icon,
                    contentDescription = "icon_$text",
                    modifier = Modifier.padding(start = Dimens.SmallPadding,end = Dimens.SmallPadding),
                    tint = textColor
                )
            }

            Text(
                text = text,
                fontSize = Dimens.SmallText,
                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold),
                color = textColor,
                modifier = Modifier.padding(horizontal = Dimens.MediumPadding),
            )
        }
    }
}