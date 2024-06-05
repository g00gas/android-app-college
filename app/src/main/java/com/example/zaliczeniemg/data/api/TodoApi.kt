package com.example.zaliczeniemg.data.api

import com.example.zaliczeniemg.data.model.TodoItem
import com.example.zaliczeniemg.data.model.TodoItemPatchRequest
import retrofit2.http.*


interface TodoApi {
    @GET("todo")
    suspend fun getTodos(): List<TodoItem>

    @GET("todo/{id}")
    suspend fun getTodoById(@Path("id") id: Long): TodoItem

    @POST("todo")
    suspend fun createTodo(@Body todoItem: TodoItemPatchRequest): TodoItem

    @PATCH("todo/{id}")
    suspend fun updateTodo(
        @Path("id") id: Long,
        @Body req: TodoItemPatchRequest
    ): TodoItem

    @DELETE("todo/{id}")
    suspend fun deleteTodo(
        @Path("id") id: Long
    ): Void
}