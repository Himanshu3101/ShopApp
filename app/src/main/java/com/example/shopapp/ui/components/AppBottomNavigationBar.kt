package com.example.shopapp.ui.components

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.shopapp.R
import com.example.shopapp.ui.navigation.BottomNavItem

@Composable
fun AppBottomNavigationBar(
    navController: NavController,
    modifier: Modifier = Modifier,
){
    val items = listOf(
        BottomNavItem.Dashboard,
        BottomNavItem.Cart,
        BottomNavItem.Profile
    )

    NavigationBar(modifier = modifier) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoutes = navBackStackEntry?.destination?.route

        items.forEach { item ->
            NavigationBarItem(
                icon = { Icon(item.icon, contentDescription = stringResource(item.labelResId)) },
                label = { Text(stringResource(item.labelResId)) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = colorResource(R.color.blue2),
                    selectedTextColor = colorResource(R.color.black),
                    indicatorColor = colorResource(R.color.lightGrey2),
                    unselectedIconColor = colorResource(R.color.blue),
                    unselectedTextColor = colorResource(R.color.darkGrey)),
                selected = currentRoutes == item.route,
                onClick = {
                    navController.navigate(item.route){
                        popUpTo(navController.graph.findStartDestination().id){
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}