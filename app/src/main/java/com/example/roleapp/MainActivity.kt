package com.example.roleapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.roleapp.adminpage.AdminViewModel
import com.example.roleapp.login.LoginViewModel
import com.example.roleapp.register.RegisterViewModel
import com.example.roleapp.ui.theme.RoleAppTheme
import com.example.roleapp.ui.theme.navigation.NavigationGraph
import com.example.roleapp.userpage.UserViewModel

class MainActivity : ComponentActivity() {
    private val loginViewModel : LoginViewModel by viewModels()
    private val registerViewModel : RegisterViewModel by viewModels()
    private val userViewModel : UserViewModel by viewModels()
    private val adminViewModel : AdminViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RoleAppTheme {
                val controller: NavHostController = rememberNavController()
                Scaffold() {paddingValues ->
                    NavigationGraph(
                        modifier = Modifier.padding(paddingValues),
                        navController = controller,
                        loginViewModel = loginViewModel,
                        registerViewModel = registerViewModel,
                        userViewModel = userViewModel,
                        adminViewModel = adminViewModel
                    )
                 }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    RoleAppTheme {
        Greeting("Android")
    }
}