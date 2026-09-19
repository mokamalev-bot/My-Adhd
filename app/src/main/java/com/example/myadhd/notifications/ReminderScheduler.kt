package com.example.myadhd.notifications

import android.content.Context
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit

object ReminderScheduler {
    fun scheduleDaily(context: Context) {
        val request = PeriodicWorkRequestBuilder<DailySupportWorker>(24, TimeUnit.HOURS).build()
        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            NotificationSetup.DAILY_WORK_NAME,
            ExistingPeriodicWorkPolicy.UPDATE,
            request
        )
    }

    fun cancel(context: Context) = WorkManager.getInstance(context).cancelUniqueWork(NotificationSetup.DAILY_WORK_NAME)
}
