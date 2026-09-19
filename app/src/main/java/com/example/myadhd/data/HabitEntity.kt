package com.example.myadhd.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "habits")
data class HabitEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val completedDates: String = ""
) {
    fun completedToday(today: String): Boolean = today in completedDates.split(',').filter { it.isNotBlank() }
    fun toggleToday(today: String): HabitEntity {
        val dates = completedDates.split(',').filter { it.isNotBlank() }.toMutableSet()
        if (!dates.add(today)) dates.remove(today)
        return copy(completedDates = dates.joinToString(","))
    }
}
