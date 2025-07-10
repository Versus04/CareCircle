package com.example.carecircle.Services

sealed class Screens(val route :String) {
    object Home : Screens("home")
    object Authenticate : Screens("auth")
}