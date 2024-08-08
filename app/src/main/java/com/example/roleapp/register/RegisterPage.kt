package com.example.roleapp.register

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowDropDown
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Person
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
import androidx.compose.ui.text.toLowerCase
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.roleapp.data.model.Role
import com.example.roleapp.ui.theme.CustomButton
import com.example.roleapp.ui.theme.CustomTextField
import com.example.roleapp.ui.theme.DropdownComponent
import com.example.roleapp.ui.theme.MainColor
import com.example.roleapp.ui.theme.PasswordField
import kotlinx.coroutines.delay

@Composable
fun RegisterScreen(navController: NavHostController, viewModel: RegisterViewModel) {
    Box(modifier = Modifier.fillMaxSize()) {
        BackHandler {
            navController.popBackStack()
        }
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
            Text(text = "Register", style = MaterialTheme.typography.displayMedium,
                color = Color.White, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.padding(20.dp))
            RegisterLoginForm(navController, viewModel)
            Spacer(modifier = Modifier.padding(30.dp))

        }
    }
}

@Composable
fun RegisterLoginForm(navController: NavHostController, viewModel: RegisterViewModel) {
    var email by remember {
        mutableStateOf(TextFieldValue(""))
    }
    var password by remember {
        mutableStateOf(TextFieldValue(""))
    }
    var username by remember {
        mutableStateOf(TextFieldValue(""))
    }

    val roles = listOf(Role.ADMIN.name.lowercase(), Role.USER.name.lowercase())

    var selectedOption by remember {
        mutableStateOf("Select an option")
    }
    val emailError by viewModel.emailValidator.observeAsState()

    val passwordError by viewModel.passwordValidator.observeAsState()

    val registerResult by viewModel.registerResult.observeAsState()

    var message by remember {
        mutableStateOf("")
    }

    var emailError_ by remember {
        mutableStateOf(false)
    }

    var passError_ by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(registerResult) {
        registerResult?.let {
            if (it) {
                message = "Register Success"
                delay(1000)
                navController.navigate("login")
            } else {
                message = "User already registered. Use another email"
            }
            viewModel.resetState()
        }
    }

    LaunchedEffect(emailError) {
        emailError?.let {
            emailError_ = it
        }
    }

    LaunchedEffect(password) {
        passwordError?.let {
            passError_ = it
        }
    }

    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(start = 30.dp, end = 30.dp), shape = RoundedCornerShape(10),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        CardDefaults.cardElevation(defaultElevation = 4.dp)) {

        Spacer(modifier = Modifier.padding(10.dp))
        CustomTextField(textField = username, onChange = {
            username=it
        }, label = "Username", icon = Icons.Outlined.Person, inputType = KeyboardType.Text)
        Spacer(modifier = Modifier.padding(10.dp))
        Column(Modifier.clickable { viewModel.validateEmail(email.text) }) {
            CustomTextField(textField = email, onChange = {
                email = it
            }, label = "Email", icon = Icons.Outlined.Email, inputType = KeyboardType.Email)
            if (emailError_) {
                Text(text = "Invalid email address", color = Color.Red,
                    modifier = Modifier.padding(start = 16.dp)
                )
            }
        }
        Spacer(modifier = Modifier.padding(10.dp))
        Column(Modifier.clickable { viewModel.validatePassword(password.text) }) {
            PasswordField(password = password, onChange = { password = it
            }, label = "password")
            if (passError_) {
                Text(text = "Password must be at least 8 characters, contains uppercase and digits", color = Color.Red,
                    modifier = Modifier.padding(start = 16.dp)
                )
            }
        }
        Spacer(modifier = Modifier.padding(10.dp))
        DropdownComponent(items = roles, selectedText = selectedOption,
            onOptionSelected = { selectedOption = it})
        Spacer(modifier = Modifier.padding(10.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentSize(align = Alignment.Center)
                .padding(bottom = 30.dp)
        ) {
            CustomButton(text = "Register", onClick = { viewModel.register(name = username.text, email = email.text,
                password= password.text, role = selectedOption) })
        }
        Spacer(modifier = Modifier.padding(20.dp))
        if (message != "") {
            Box(modifier = Modifier.background(MainColor)) {
                Text(text = message, modifier = Modifier.padding(10.dp))
            }
        }

//        registerResult?.let {
//            Text(if (it) "Register Successful" else "Email has been registered")
//        }


    }
}

@Preview(showSystemUi = true)
@Composable
fun RegisterPreview() {
}