package com.example.carecircle.services

sealed class Screens(val route :String) {
    object Home : Screens("home")
    object Authenticate : Screens("auth")
}