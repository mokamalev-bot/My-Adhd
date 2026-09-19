package com.example.myadhd.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [TaskEntity::class, HabitEntity::class, DailyCheckInEntity::class], version = 3, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
    abstract fun habitDao(): HabitDao
    abstract fun dailyCheckInDao(): DailyCheckInDao

    companion object {
        @Volatile private var instance: AppDatabase? = null
        private val migration1To2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("CREATE TABLE IF NOT EXISTS habits (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, name TEXT NOT NULL, completedDates TEXT NOT NULL DEFAULT '')")
            }
        }
        private val migration2To3 = object : Migration(2, 3) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("CREATE TABLE IF NOT EXISTS daily_checkins (date TEXT NOT NULL PRIMARY KEY, energy INTEGER NOT NULL, focus INTEGER NOT NULL, stress INTEGER NOT NULL, createdAt INTEGER NOT NULL)")
            }
        }
        fun get(context: Context): AppDatabase = instance ?: synchronized(this) {
            instance ?: Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "my_adhd.db")
                .addMigrations(migration1To2, migration2To3)
                .build().also { instance = it }
        }
    }
}
