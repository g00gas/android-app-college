package com.example.zaliczeniemg.ui.todoList

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.zaliczeniemg.data.model.TodoItem
import com.example.zaliczeniemg.data.model.TodoItemPatchRequest
import kotlinx.coroutines.launch
import java.sql.Date

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TodoItemCard(todoItem: TodoItem, onTodoClicked: () -> Unit) {
    val viewModel: TodoViewModel = viewModel()
    val coroutineScope = rememberCoroutineScope()
    var showDialog by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .combinedClickable(
                onClick = {},
                onLongClick = { onTodoClicked() },
                interactionSource = remember {
                    MutableInteractionSource()
                },
                indication = rememberRipple(bounded = true),
            )
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = todoItem.title,
                    style = MaterialTheme.typography.headlineSmall
                )
                IconButton(onClick = { showDialog = true }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete Todo"
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = todoItem.content, style = MaterialTheme.typography.bodyLarge)
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Created by ${todoItem.author}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Switch(checked = todoItem.completed, onCheckedChange = {
                    coroutineScope.launch {
                        val request = TodoItemPatchRequest(completed = !todoItem.completed)
                        viewModel.updateTodo(todoItem.id, request)
                    }
                })
            }
        }
    }
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Delete Todo") },
            text = { Text("Are you sure you want to delete this todo item?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        coroutineScope.launch {
                            viewModel.deleteTodo(todoItem.id)
                            showDialog = false
                        }

                    }
                ) {
                    Text("Delete")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showDialog = false }
                ) {
                    Text("Cancel")
                }
            }
        )
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewTodoItemRow() {
    TodoItemCard(todoItem = TodoItem(
        id = 1,
        content = "Sample Todo",
        completed = false,
        author = "me",
        creationDate = Date(1717346159),
        title = "test"
    ), onTodoClicked = {})
}