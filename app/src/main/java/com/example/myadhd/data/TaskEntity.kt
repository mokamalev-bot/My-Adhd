package com.example.myadhd.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
data class TaskEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val description: String = "",
    val estimatedMinutes: Int = 15,
    val completed: Boolean = false,
    val createdAt: Long = System.currentTimeMillis()
)
