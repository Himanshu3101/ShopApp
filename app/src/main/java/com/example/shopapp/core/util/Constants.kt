package com.example.shopapp.core.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.unit.Dp


object Constants {

    @Composable
    fun deviceSize() : Dp {
        val density = LocalDensity.current
        val heightPx = LocalWindowInfo.current.containerSize.height

        return with(density) { heightPx.toDp() } // convert pixels to Dp safely
    }

    const val Slog:String = "Shop_log"
}