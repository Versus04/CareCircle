package com.example.carecircle.Presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.amplifyframework.ui.authenticator.ui.Authenticator

@Composable
fun AuthFlow(navController: NavHostController , modifier: Modifier) {
    Column(modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
    Authenticator { authState ->
        // Trigger nav only once per user session
        LaunchedEffect(authState.user.userId) {
            navController.navigate("home") {
                popUpTo("auth") { inclusive = true }
            }
        }
    }
    }
}


