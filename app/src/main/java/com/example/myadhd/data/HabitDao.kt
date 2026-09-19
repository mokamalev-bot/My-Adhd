package com.example.myadhd.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface HabitDao {
    @Query("SELECT * FROM habits ORDER BY id DESC")
    fun observeHabits(): Flow<List<HabitEntity>>
    @Insert suspend fun insert(habit: HabitEntity)
    @Update suspend fun update(habit: HabitEntity)
    @Query("DELETE FROM habits WHERE id = :id") suspend fun delete(id: Long)
}
