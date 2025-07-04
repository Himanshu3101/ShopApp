package com.example.shopapp.features.cart.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.shopapp.R
import com.example.shopapp.features.common.components.AppBar
import com.example.shopapp.ui.navigation.Routes
import com.example.shopapp.ui.theme.Dimens

@Preview
@Composable
fun Prev_CartUI(){
    CartUI(navController = rememberNavController())
}

@Composable
fun CartUI(
    navController: NavHostController
){
    Box(
        modifier = Modifier.fillMaxSize()
    ){

        Column (
            modifier = Modifier.fillMaxSize().background(colorResource(R.color.white))
        ){

            //Topbar
            AppBar(title = "My Cart") {
                navController.navigate(Routes.Dashboard.route){
                    popUpTo(Routes.Dashboard.route){inclusive = true}
                }
            }
        }
    }
}