package com.example.todo.data

import java.util.UUID

data class TodoItem(
    val text: String,
    val completed: Boolean = false,
    val guid: String = UUID.randomUUID().toString()
)