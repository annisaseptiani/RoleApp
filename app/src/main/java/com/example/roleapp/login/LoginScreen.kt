package com.example.roleapp.login

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowDropDown
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.roleapp.ui.theme.CustomButton
import com.example.roleapp.ui.theme.CustomTextField
import com.example.roleapp.ui.theme.MainColor
import com.example.roleapp.ui.theme.PasswordField
import kotlinx.coroutines.delay

@Composable
fun LoginScreen(navController: NavController, viewModel: LoginViewModel) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .height(300.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(bottomEnd = 30.dp, bottomStart = 30.dp))
                .background(color = MainColor)) {
        }
        Column(modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally) {
            Spacer(modifier = Modifier.padding(30.dp))
            Text(text = "Login", style = MaterialTheme.typography.displayMedium,
                color = Color.White, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.padding(20.dp))
            CardLoginForm(navController, viewModel)
            Spacer(modifier = Modifier.padding(30.dp))
            CardRegister(navController)
        }
    }
}

@Composable
fun CardLoginForm(navController: NavController, viewModel: LoginViewModel) {
    var email by remember {
        mutableStateOf(TextFieldValue(""))
    }
    var password by remember {
        mutableStateOf(TextFieldValue(""))
    }
    val loginResult by viewModel.loginResult.observeAsState()

    var message by remember {
        mutableStateOf("")
    }

    LaunchedEffect(loginResult) {
        loginResult?.let {
            if (it != "") {
                message = "Login Successful"
                delay(1000)
                navController.navigate(it)
            } else {
                message = "User not found. Wrong password/email not registered"
            }
        }
        viewModel.resetState()
    }

    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(start = 30.dp, end = 30.dp), shape = RoundedCornerShape(10),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        CardDefaults.cardElevation(defaultElevation = 4.dp)) {
        Spacer(modifier = Modifier.padding(30.dp))
        CustomTextField(textField = email, onChange = {
            email=it
        }, label = "Email", icon = Icons.Outlined.Email, inputType = KeyboardType.Email)
        Spacer(modifier = Modifier.padding(30.dp))
        PasswordField(password = password, onChange = { password = it
        }, label = "password")
        Spacer(modifier = Modifier.padding(30.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentSize(align = Alignment.Center)
                .padding(bottom = 30.dp)
        ) {
            CustomButton(text = "Login", onClick = { viewModel.login(email.text, password.text) })
        }
        Spacer(modifier = Modifier.padding(20.dp))
        if (message != "") {
            Box(modifier = Modifier.background(MainColor).align(Alignment.CenterHorizontally)) {
                Text(text = message, modifier = Modifier.padding(10.dp))
            }
        }


    }
}

@Composable
fun CardRegister(navController: NavController) {
    Card(
        shape = RoundedCornerShape(20),
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 30.dp, end = 30.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(modifier = Modifier
            .padding(20.dp)
            .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center, ) {
            Text(text = "Do you want to register?", style = MaterialTheme.typography.bodyMedium)
            Row(Modifier.padding(start = 2.dp).clickable { navController.navigate("register") }) {
                Text(text = "Register", style = MaterialTheme.typography.bodyMedium, color = MainColor)
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun LoginPreview() {
}