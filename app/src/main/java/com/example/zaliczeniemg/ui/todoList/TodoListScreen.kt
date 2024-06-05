package com.example.todoapp.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.zaliczeniemg.data.model.TodoItem
import com.example.zaliczeniemg.data.repository.TodoRepository
import com.example.zaliczeniemg.ui.todoList.SetUsernameDialog
import com.example.zaliczeniemg.ui.todoList.TodoItemCard
import com.example.zaliczeniemg.ui.todoList.TodoItemEditor
import com.example.zaliczeniemg.ui.todoList.TodoViewModel
import java.sql.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoListScreen() {
    val viewModel: TodoViewModel = viewModel()
    val repository = TodoRepository()
    var todos by remember { mutableStateOf(listOf<TodoItem>()) }
    val coroutineScope = rememberCoroutineScope()
    var selectedItem by remember { mutableStateOf<TodoItem?>(null) }
    val refreshFlag by viewModel.refreshFlag.collectAsState()
    val sharedPreferences = androidx.preference.PreferenceManager.getDefaultSharedPreferences(LocalContext.current)
    var username by remember { mutableStateOf(sharedPreferences.getString("username", "") ?: "") }
    var isEditorOpen by remember { mutableStateOf(false) }
    LaunchedEffect(refreshFlag) {
        try {
            todos = viewModel.getTodos()
        } catch (e: Exception) {
            // Handle the error, for example by logging it or showing a message to the user
            e.printStackTrace()
            System.err.println(e.localizedMessage)
        }
    }

    val setUsername: (String) -> Unit = { newUsername ->
        username = newUsername
        sharedPreferences.edit().putString("username", newUsername).apply()
    }

    Scaffold(topBar = { TopAppBar(title = { Text("Todo List") }) }, content = { padding ->
        LazyColumn(
            contentPadding = padding, modifier = Modifier.fillMaxSize()
        ) {
            items(todos) { todo ->
                TodoItemCard(todoItem = todo, onTodoClicked = { selectedItem = todo })
            }
        }
    }, floatingActionButton = {
        FloatingActionButton(onClick = { isEditorOpen = true }) {
            Icon(Icons.Default.Add, contentDescription = "Add Todo")
        }
    })
    selectedItem?.let { todo ->
        TodoItemEditor(todoItem = todo, onDismiss = { selectedItem = null }, isNew = false)
    }
    if (username.isEmpty()) {
        SetUsernameDialog(onUsernameSet = setUsername)
    }
    if (isEditorOpen) {
        TodoItemEditor(
            todoItem = null,
            onDismiss = { isEditorOpen = false },
            isNew = true,
        )
    }
}


@Preview(showBackground = true)
@Composable
fun previewTodoListScreen() {
    val mockTodos = listOf(
        TodoItem(
            id = 1,
            content = "Buy groceries",
            completed = false,
            author = "me",
            creationDate = Date(1717346159),
            title = "test1"
        ), TodoItem(
            id = 2,
            content = "Call mom",
            completed = true,
            author = "me",
            creationDate = Date(1517346159),
            title = "test2"
        ), TodoItem(
            id = 3,
            content = "Finish homework",
            completed = false,
            author = "me",
            creationDate = Date(1717146159),
            title = "test3"
        )
    )
    Column {
        mockTodos.forEach { todo ->
            TodoItemCard(todoItem = todo, onTodoClicked = {})
        }
    }
}


@Preview(showBackground = true)
@Composable
fun previewFullView() {
    TodoListScreen()
}