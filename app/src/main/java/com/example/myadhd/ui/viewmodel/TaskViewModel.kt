package com.example.myadhd.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.myadhd.data.*
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class TaskViewModel(app: Application) : AndroidViewModel(app) {
    private val dao = AppDatabase.get(app).taskDao()
    val tasks = dao.observeTasks().stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())
    fun add(title: String, minutes: Int = 15) { if (title.isNotBlank()) viewModelScope.launch { dao.insert(TaskEntity(title = title.trim(), estimatedMinutes = minutes)) } }
    fun toggle(task: TaskEntity) = viewModelScope.launch { dao.update(task.copy(completed = !task.completed)) }
    fun delete(task: TaskEntity) = viewModelScope.launch { dao.delete(task.id) }
}
