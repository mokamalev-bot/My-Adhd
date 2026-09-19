package com.example.myadhd.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Query("SELECT * FROM tasks ORDER BY completed ASC, createdAt DESC")
    fun observeTasks(): Flow<List<TaskEntity>>
    @Insert suspend fun insert(task: TaskEntity)
    @Update suspend fun update(task: TaskEntity)
    @Query("DELETE FROM tasks WHERE id = :id") suspend fun delete(id: Long)
}
