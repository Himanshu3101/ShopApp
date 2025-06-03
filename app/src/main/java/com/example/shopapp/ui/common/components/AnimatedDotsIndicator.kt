package com.example.shopapp.ui.common.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.Dp
import com.example.shopapp.ui.theme.Dimens

@Composable
fun AnimatedDotsIndicator(
    totalDots: Int,
    selectedIndex: Int,
    modifier: Modifier = Modifier,
    activeColor : Color = Color.Black,
    inactiveColor : Color = Color.LightGray,
    dotSize : Dp = Dimens.SmallDot,
    selectDotSize : Dp = Dimens.MediumDot,
    dotSpacing: Dp = Dimens.SmallDot,
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(dotSpacing),
        modifier = modifier
    ){
        repeat (totalDots) { index ->
            val isSelected = index == selectedIndex

            val animatedSize by animateDpAsState(
                targetValue = if (isSelected) selectDotSize else dotSize,
                label = "DotSizeAnimation"
            )

            val animatedAlpha by animateFloatAsState(
                targetValue = if (isSelected) 1f else 0.5f,
                label = "DotAlphaAnimation"
            )

            Box(
                modifier = Modifier
                    .size(animatedSize)
                    .graphicsLayer{alpha = animatedAlpha}
                    .clip(CircleShape)
                    .background(if(isSelected) activeColor else inactiveColor)
            ){

            }
        }
    }
}