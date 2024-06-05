package com.example.zaliczeniemg.data.model

data class TodoItemPatchRequest(
    val creationDate: java.util.Date? = null,
    val content: String? = null,
    val author: String? = null,
    val completed: Boolean? = null,
    val title: String? = null
)