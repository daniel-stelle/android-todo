package com.example.todo.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity
data class TodoItem(
    val text: String = "",
    val completed: Boolean = false,
    @PrimaryKey val guid: String = UUID.randomUUID().toString()
)