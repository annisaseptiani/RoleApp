package com.example.roleapp.ui.theme.navigation

sealed class Routes(val routes : String) {
    object Login : Routes("login")
    object Register : Routes("register")
    object UserPage : Routes("user")
    object AdminPage : Routes("admin")
}