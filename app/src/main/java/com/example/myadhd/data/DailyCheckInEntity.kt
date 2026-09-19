package com.example.myadhd.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "daily_checkins")
data class DailyCheckInEntity(
    @PrimaryKey val date: String,
    val energy: Int,
    val focus: Int,
    val stress: Int,
    val createdAt: Long = System.currentTimeMillis()
)
