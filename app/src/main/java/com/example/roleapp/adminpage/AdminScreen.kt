package com.example.roleapp.adminpage

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.example.roleapp.data.model.Role
import com.example.roleapp.data.model.UserEntity
import com.example.roleapp.ui.theme.CustomButton
import com.example.roleapp.ui.theme.CustomTextField
import com.example.roleapp.ui.theme.MainColor
import kotlinx.coroutines.delay

@Composable
fun AdminScreen(navController: NavController, viewModel: AdminViewModel) {
    val showEdit by viewModel.isEditDialogOpen.collectAsState()
    val showVerifyPassword by viewModel.isDeleteDialogOpen.collectAsState()
    var selectedItem by remember {
        mutableStateOf<UserEntity?>(null)
    }
    val userList by viewModel.userList.collectAsState()

    val isDeleteItem by viewModel.deleteItem.collectAsState()

    LaunchedEffect(userList) {
        viewModel.listUser()
    }

    LaunchedEffect(isDeleteItem) {
        viewModel.listUser()
    }


    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .height(30.dp)
                    .background(MainColor), horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.weight(1F),
                    text = "Id",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.White
                )
                Text(
                    modifier = Modifier.weight(2F),
                    text = "Username",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.White
                )
                Text(
                    modifier = Modifier.weight(3F),
                    text = "Email",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.White
                )
                Text(
                    modifier = Modifier.weight(2F),
                    text = "Role",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.White
                )
                Text(
                    modifier = Modifier.weight(2F),
                    text = "Action",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.White
                )
            }
            LazyColumn {
                items(userList) {
                    UserItem(user = it,
                        onClickDelete = {
                            selectedItem = it
                            viewModel.showDeleteDialog()
                        },
                        onClickEdit = {
                        selectedItem = it
                        viewModel.showEditDialog()
                    })
                }
            }
        }
        if (showVerifyPassword) {
            InputPassword(viewModel = viewModel, id = selectedItem!!.id)
        }
        if (showEdit) {
            Column(
                Modifier
                    .align(Alignment.Center)
                    .fillMaxHeight(), verticalArrangement = Arrangement.Center
            ) {

                EditUser(viewModel, selectedItem!!)
            }
        }
    }
}

@Composable
fun InputPassword(viewModel: AdminViewModel, id : Int) {
    val isDialogOpen by viewModel._isDeleteDialogOpen.collectAsState()

    var password by remember {
        mutableStateOf(TextFieldValue(""))
    }

    var error by remember {
        mutableStateOf(false)
    }

    val isPasswordVerified by viewModel.verifyPass.collectAsState()
    val context = LocalContext.current
    LaunchedEffect(isPasswordVerified) {
        isPasswordVerified?.let {
            if (it) {
                viewModel.deleteItem(id)
                Toast.makeText(context, "Data successfully deleted!", Toast.LENGTH_SHORT).show()
                delay(1000)
                viewModel.hideDeleteDialog()
                error = false
            } else {
                error = true
            }
        }
    }


    if (isDialogOpen) {
        Dialog(onDismissRequest = { viewModel.hideDeleteDialog() }) {
            Card(Modifier.wrapContentSize()) {
                Text(text = "Input Password To Delete")
                CustomTextField(textField = password, onChange = { password = it},
                    label = "Password", icon = Icons.Outlined.Lock, inputType = KeyboardType.Password)
                CustomButton(text = "Confirm", onClick = { viewModel.verifyPassword(password.text) })
                if (error) {
                    Text(text = "Wrong password", color=Color.Red)
                }
            }
        }
    }



}

@Composable
fun UserItem(user : UserEntity, onClickDelete : () -> Unit, onClickEdit : () -> Unit) {
    Row(
        Modifier
            .fillMaxWidth()
            .height(30.dp)
            .background(Color.White), horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically) {
        Text(modifier = Modifier.weight(1F), text = user.id.toString(), style = MaterialTheme.typography.bodySmall)
        Text(modifier = Modifier.weight(2F), text = user.name, style = MaterialTheme.typography.bodySmall)
        Text(modifier = Modifier.weight(3F), text = user.email, style = MaterialTheme.typography.bodySmall)
        Text(modifier = Modifier.weight(2F), text = user.role, style = MaterialTheme.typography.bodySmall)
        Row(modifier = Modifier.weight(2F)) {
            Box(modifier = Modifier
                .clickable { onClickEdit() }) {
                Icon(imageVector = Icons.Outlined.Edit, contentDescription = "edit icon")
            }
            Spacer(modifier = Modifier.padding(5.dp))
            Box(modifier = Modifier.clickable { onClickDelete() }) {
                Icon(imageVector = Icons.Outlined.Delete, contentDescription = "delete icon")
            }

        }
    }
}



@Preview(showSystemUi = true)
@Composable
fun AdminPreview() {
}

