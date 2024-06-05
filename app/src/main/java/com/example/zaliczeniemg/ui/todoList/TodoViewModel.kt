package com.example.zaliczeniemg.ui.todoList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.zaliczeniemg.data.model.TodoItem
import com.example.zaliczeniemg.data.model.TodoItemPatchRequest
import com.example.zaliczeniemg.data.repository.TodoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TodoViewModel(private val repository: TodoRepository) : ViewModel() {
    constructor() : this(TodoRepository())

    private val _refreshFlag = MutableStateFlow(false)
    val refreshFlag: StateFlow<Boolean> = _refreshFlag.asStateFlow()

    suspend fun getTodos(): List<TodoItem> {
        return repository.getTodos()
    }

    fun updateTodo(id: Long, request: TodoItemPatchRequest) {
        viewModelScope.launch {
            try {
                repository.updateTodo(id, request)
                _refreshFlag.value = !_refreshFlag.value
            } catch (e: Exception) {
                println("Error updating todo: ${e.localizedMessage}")
            }
        }
    }

    fun createTodo(request: TodoItemPatchRequest) {
        viewModelScope.launch {
            try {
                repository.createTodo(request)
                _refreshFlag.value = !_refreshFlag.value
            } catch (e: Exception) {
                println("Error creating todo: ${e.localizedMessage}")
            }
        }
    }

    fun deleteTodo(id: Long) {
        viewModelScope.launch {
            try {
                repository.deleteTodo(id)
                _refreshFlag.value = !_refreshFlag.value
            } catch (e: Exception) {
                println("Error deleting todo: ${e.localizedMessage}")
            }
        }
    }
}