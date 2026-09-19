package com.example.myadhd.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface DailyCheckInDao {
    @Query("SELECT * FROM daily_checkins WHERE date = :date LIMIT 1")
    fun observe(date: String): Flow<DailyCheckInEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(checkIn: DailyCheckInEntity)
}
