package com.example.roleapp.ui.theme

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun CustomTextField(textField: TextFieldValue, onChange : (TextFieldValue) -> Unit, label:String, icon: ImageVector,
                    endIcon : ImageVector = Icons.Outlined.Close, inputType : KeyboardType) {
    OutlinedTextField(modifier = Modifier.padding(start = 30.dp, end = 30.dp).fillMaxWidth(), value = textField,
        onValueChange = onChange, label = { Text(label) },
        placeholder = { Text(label)}, leadingIcon = { Icon(icon, contentDescription = null) },
        trailingIcon = {
            if (textField.text.isNotEmpty()) {
                IconButton(onClick = { onChange(TextFieldValue("")) }) {
                    Icon(endIcon, contentDescription = "Clear text")
                }
            } },
        keyboardOptions = KeyboardOptions(keyboardType = inputType)
    )
}

@Composable
fun CustomButton(text: String, onClick : () -> Unit) {
    Button(onClick = { onClick()}, colors = ButtonDefaults.buttonColors(containerColor = MainColor)) {
        Text(modifier = Modifier.padding(10.dp), text = text, color = Color.White)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownComponent(
    items: List<String>, selectedText : String, onOptionSelected : (String)->Unit
) {
    var expanded by remember { mutableStateOf(false) }
    Column(modifier = Modifier.padding(start = 30.dp, end = 30.dp),) {
        Box() {
            ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = {
                expanded = !expanded
            }) {
                OutlinedTextField(
                    value = selectedText,
                    onValueChange = {},
                    readOnly = true,
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth()
                )
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    items.forEach { item ->
                        DropdownMenuItem(
                            text = { Text(text = item) },
                            onClick = {
                                onOptionSelected(item)
                                expanded = false
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun PasswordField(password: TextFieldValue, onChange : (TextFieldValue) -> Unit,
                  label: String) {

    var passwordVisible by remember { mutableStateOf(false) }
        OutlinedTextField(
            modifier = Modifier.padding(start = 30.dp, end= 30.dp).fillMaxWidth(),
            value = password,
            onValueChange = onChange,
            label = { Text(label) },
            singleLine = true,
            leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) },
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                val image = if (passwordVisible) {
                    Icons.Filled.KeyboardArrowUp
                } else {
                    Icons.Filled.KeyboardArrowDown
                }
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(imageVector = image, contentDescription = null)
                }
            }
        )
}

