package com.example.myadhd.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.myadhd.data.AppDatabase
import com.example.myadhd.data.DailyCheckInEntity
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate

class CheckInViewModel(application: Application) : AndroidViewModel(application) {
    private val dao = AppDatabase.get(application).dailyCheckInDao()
    private val today = LocalDate.now().toString()
    val todayCheckIn = dao.observe(today).stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), null)

    fun save(energy: Int, focus: Int, stress: Int) = viewModelScope.launch {
        dao.save(DailyCheckInEntity(today, energy.coerceIn(1, 3), focus.coerceIn(1, 3), stress.coerceIn(1, 3)))
    }
}
