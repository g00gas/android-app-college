package com.example.zaliczeniemg.data.model

data class TodoItem(
    val id: Long,
    val creationDate: java.util.Date,
    val content: String,
    val author: String,
    val completed: Boolean,
    val title: String
)