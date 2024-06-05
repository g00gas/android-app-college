package com.example.zaliczeniemg.data.model

import java.text.SimpleDateFormat
import java.util.*

data class TodoItemPatchRequest(
    val creationDate: String? = null,
    val content: String? = null,
    val author: String? = null,
    val completed: Boolean? = null,
    val title: String? = null

) {
    companion object {
        private val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US)

        fun fromDate(date: Date?): String? {
            return date?.let { dateFormat.format(it) }
        }
    }
}
