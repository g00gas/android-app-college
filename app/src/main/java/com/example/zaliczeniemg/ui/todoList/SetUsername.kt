package com.example.zaliczeniemg.ui.todoList

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*

@Composable
fun SetUsernameDialog(onUsernameSet: (String) -> Unit) {
    var username by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = { /* Handle dialog closing */ },
        title = { Text("Set Username") },
        text = {
            Column {
                TextField(
                    value = username,
                    onValueChange = { username = it },
                    label = { Text("Enter your username") }
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    onUsernameSet(username)
                }
            ) {
                Text("Set")
            }
        }
    )
}