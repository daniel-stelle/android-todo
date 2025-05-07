package com.example.prayerly.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Calendar
import java.util.UUID

@Entity
data class PrayerItem(
    val text: String = "",
    val completed: Boolean = false,
    val createdAt: Long = Calendar.getInstance().time.time,
    @PrimaryKey val guid: String = UUID.randomUUID().toString()
)