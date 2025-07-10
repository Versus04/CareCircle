package com.example.carecircle.Services

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.carecircle.Presentation.AuthFlow
import com.example.carecircle.Presentation.HomeScreen

@Composable
fun Navi(navController: NavHostController , modifier: Modifier)
{
    NavHost(navController , startDestination = Screens.Authenticate.route) {
        composable(Screens.Home.route)
        {
            HomeScreen()
        }
        composable(Screens.Authenticate.route){
            AuthFlow(navController , modifier = Modifier)
        }
    }
}
