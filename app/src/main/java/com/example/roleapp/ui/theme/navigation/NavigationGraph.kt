package com.example.roleapp.ui.theme.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.roleapp.adminpage.AdminScreen
import com.example.roleapp.adminpage.AdminViewModel
import com.example.roleapp.login.LoginScreen
import com.example.roleapp.login.LoginViewModel
import com.example.roleapp.register.RegisterScreen
import com.example.roleapp.register.RegisterViewModel
import com.example.roleapp.userpage.UserScreen
import com.example.roleapp.userpage.UserViewModel

@Composable
fun NavigationGraph(modifier: Modifier, navController: NavHostController,
                    loginViewModel: LoginViewModel, registerViewModel: RegisterViewModel,
                    userViewModel: UserViewModel, adminViewModel: AdminViewModel) {
    NavHost(modifier = modifier.fillMaxSize(),navController = navController, startDestination = Routes.Login.routes) {
        composable(Routes.Login.routes){
            LoginScreen(navController = navController, viewModel = loginViewModel)
        }
        composable(Routes.Register.routes){
            RegisterScreen(navController = navController, viewModel = registerViewModel)
        }
        composable(Routes.AdminPage.routes){
            AdminScreen(navController = navController, viewModel = adminViewModel)
        }
        composable(Routes.UserPage.routes){
            UserScreen(navController = navController, viewModel = userViewModel)
        }
    }
}