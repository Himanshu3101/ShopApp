package com.example.shopapp.core.util

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.unit.Dp



object Constants {

    const val DATABASE_NAME = "shop_db"

    @Composable
    fun deviceSize() : Dp {
        val density = LocalDensity.current
        val heightPx = LocalWindowInfo.current.containerSize.height

        return with(density) { heightPx.toDp() } // convert pixels to Dp safely
    }

    const val Shoplog:String = "Shop_log"

    fun showToast(context: Context, message:String, duration: Int = Toast.LENGTH_SHORT ){
        Toast.makeText(context, message, duration).show()
    }
}