package com.example.myadhd

import android.app.Application
import com.example.myadhd.notifications.NotificationSetup
import com.example.myadhd.notifications.ReminderScheduler

class MyAdhdApp : Application() {
    override fun onCreate() {
        super.onCreate()
        NotificationSetup.createChannels(this)
        ReminderScheduler.scheduleDaily(this)
    }
}
