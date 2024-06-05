package com.example.zaliczeniemg.ui.todoList

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.zaliczeniemg.data.model.TodoItem
import com.example.zaliczeniemg.data.model.TodoItemPatchRequest
import java.sql.Date
import java.time.LocalDate
import java.time.ZoneId

@Composable
fun TodoItemEditor(todoItem: TodoItem?, onDismiss: () -> Unit, isNew: Boolean) {
    val sharedPreferences = androidx.preference.PreferenceManager.getDefaultSharedPreferences(LocalContext.current)
    var title by remember { mutableStateOf(if (isNew) "" else todoItem?.title ?: "") }
    var content by remember { mutableStateOf(if (isNew) "" else todoItem?.content ?: "") }
    var author by remember { mutableStateOf(todoItem?.author ?: sharedPreferences.getString("username", "")) }
    val viewModel: TodoViewModel = viewModel()

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .padding(16.dp)
            ) {
                Text(
                    text = if (isNew) "Create Todo" else "Edit Todo",
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                TextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Title") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    TextField(
                        value = content,
                        onValueChange = { content = it },
                        label = { Text("Content") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight()
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                }
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Button(
                        onClick = onDismiss,
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Cancel")
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Button(
                        onClick = {
                            if (isNew) {
                                val currentDate =
                                    Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant())
                                val request = TodoItemPatchRequest(
                                    title = title,
                                    content = content,
                                    creationDate = TodoItemPatchRequest.fromDate(currentDate),
                                    author = author,
                                    completed = false
                                )
                                viewModel.createTodo(request)

                            } else {
                                val request =
                                    TodoItemPatchRequest(title = title, content = content, author = author)
                                viewModel.updateTodo(todoItem!!.id, request)
                            }

                            onDismiss()
                        },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Save")
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTodoItemEditor() {
    TodoItemEditor(
        todoItem = TodoItem(
            id = 1,
            content = "Sample Todo",
            completed = false,
            author = "me",
            creationDate = Date(1717346159),
            title = "test"
        ),
        onDismiss = {},
        isNew = false
    )
}