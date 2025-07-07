package com.example.shopapp.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.shopapp.R

sealed class BottomNavItem(
    val route: String,
    val icon: ImageVector,
    val labelResId: Int
){
    object Dashboard : BottomNavItem(Routes.Dashboard.route, icon = Icons.Default.Home, labelResId = R.string.home)
    object Cart : BottomNavItem(Routes.cartUI.route, icon = Icons.Default.ShoppingCart, labelResId = R.string.cart)
    object Profile : BottomNavItem(Routes.ProfileUI.route, icon = Icons.Default.Person, labelResId = R.string.person)
}