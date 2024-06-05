package com.example.zaliczeniemg.data.repository

import com.example.zaliczeniemg.data.api.RetrofitClient
import com.example.zaliczeniemg.data.model.TodoItem
import com.example.zaliczeniemg.data.model.TodoItemPatchRequest

class TodoRepository {
    private val api = RetrofitClient.api

    suspend fun getTodos(): List<TodoItem> = api.getTodos()

    suspend fun createTodo(todoItem: TodoItemPatchRequest): TodoItem = api.createTodo(todoItem)

    suspend fun updateTodo(id: Long, req: TodoItemPatchRequest): TodoItem = api.updateTodo(id, req)

    suspend fun deleteTodo(
        id: Long
    ): Void = api.deleteTodo(id)
}