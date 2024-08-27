package com.example.signlanguagetranslator.utils

sealed class Routes(
    val route: String
) {
    data object Home : Routes("home")
    data object Camera : Routes("camera")
}
