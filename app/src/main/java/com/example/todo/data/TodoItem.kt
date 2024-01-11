package com.example.todo.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Date
import java.time.Instant
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalField
import java.util.Calendar
import java.util.UUID

@Entity
data class TodoItem(
    val text: String = "",
    val completed: Boolean = false,
    val createdAt: Long = Calendar.getInstance().time.time,
    @PrimaryKey val guid: String = UUID.randomUUID().toString()
)