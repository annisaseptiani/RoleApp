package com.example.roleapp.adminpage

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountBox
import androidx.compose.material.icons.outlined.ArrowDropDown
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.roleapp.data.model.Role
import com.example.roleapp.data.model.UserEntity
import com.example.roleapp.ui.theme.CustomButton
import com.example.roleapp.ui.theme.CustomTextField
import com.example.roleapp.ui.theme.DropdownComponent
import com.example.roleapp.ui.theme.MainColor
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.observeOn

@Composable
fun EditUser(adminViewModel: AdminViewModel, userEntity: UserEntity) {
    val isDialogOpen by adminViewModel._isEditDialogOpen.collectAsState()
    var email by remember {
        mutableStateOf(TextFieldValue(userEntity.email))
    }
    var username by remember {
        mutableStateOf(TextFieldValue(userEntity.name))
    }

    val roles = listOf(Role.ADMIN.name.lowercase(), Role.USER.name.lowercase())

    var selectedOption by remember {
        mutableStateOf(userEntity.role)
    }
    val emailError by adminViewModel.emailValidator.observeAsState()

    val successEdit by adminViewModel.editItem.collectAsState()

    val context = LocalContext.current
    LaunchedEffect(successEdit) {
        successEdit?.let {
            if (it) {
                Toast.makeText(context, "Data successfully updated!", Toast.LENGTH_SHORT).show()
                delay(1000)
                adminViewModel.hideEditDialog()
            }
        }
    }
    if (isDialogOpen) {
        Dialog(onDismissRequest = {adminViewModel.hideEditDialog()}) {
            Card(modifier = Modifier
                .fillMaxWidth()
                .padding(start = 30.dp, end = 30.dp), shape = RoundedCornerShape(10),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                CardDefaults.cardElevation(defaultElevation = 4.dp)) {

                Spacer(modifier = Modifier.padding(10.dp))
                CustomTextField(textField = username, onChange = {
                    email = it
                }, label = "Username", icon = Icons.Outlined.Person, inputType = KeyboardType.Text)
                Spacer(modifier = Modifier.padding(10.dp))
                Column(Modifier.clickable { adminViewModel.validateEmail(email.text) }) {
                    CustomTextField(textField = email, onChange = {
                        email = it
                    }, label = "Email", icon = Icons.Outlined.Email, inputType = KeyboardType.Email)
                    if (emailError!!) {
                        Text(
                            text = "Invalid email address", color = Color.Red,
                            modifier = Modifier.padding(start = 16.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.padding(10.dp))
                DropdownComponent(items = roles, selectedText = selectedOption,
                    onOptionSelected = { selectedOption = it })
                Spacer(modifier = Modifier.padding(10.dp))
                Row {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentSize(align = Alignment.Center)
                            .padding(bottom = 10.dp)
                    ) {
                        CustomButton(text = "Update", onClick = { adminViewModel.editItem(name = username.text,
                            email = email.text, role = selectedOption, id = userEntity.id)})
                    }
                    CustomButton(text = "Cancel", onClick = {  })
                }
            }
        }
    }

}