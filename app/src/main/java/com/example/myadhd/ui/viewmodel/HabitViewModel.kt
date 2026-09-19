package com.example.myadhd.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.myadhd.data.AppDatabase
import com.example.myadhd.data.HabitEntity
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate

class HabitViewModel(application: Application) : AndroidViewModel(application) {
    private val dao = AppDatabase.get(application).habitDao()
    val habits = dao.observeHabits().stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())
    private val today get() = LocalDate.now().toString()

    fun add(name: String) { if (name.isNotBlank()) viewModelScope.launch { dao.insert(HabitEntity(name = name.trim())) } }
    fun toggle(habit: HabitEntity) = viewModelScope.launch { dao.update(habit.toggleToday(today)) }
    fun delete(habit: HabitEntity) = viewModelScope.launch { dao.delete(habit.id) }
}
