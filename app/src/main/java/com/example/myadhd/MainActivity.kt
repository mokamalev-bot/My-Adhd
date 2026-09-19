package com.example.myadhd

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myadhd.ui.AppRoot
import com.example.myadhd.ui.theme.MyAdhdTheme
import com.example.myadhd.ui.viewmodel.CheckInViewModel
import com.example.myadhd.ui.viewmodel.HabitViewModel
import com.example.myadhd.ui.viewmodel.PreferencesViewModel
import com.example.myadhd.ui.viewmodel.TaskViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(state: Bundle?) {
        super.onCreate(state)
        enableEdgeToEdge()
        setContent {
            val preferences = viewModel<PreferencesViewModel>()
            val tasks = viewModel<TaskViewModel>()
            val habits = viewModel<HabitViewModel>()
            val checkIns = viewModel<CheckInViewModel>()
            MyAdhdTheme(darkTheme = preferences.preferences.value.darkMode) {
                Surface(Modifier.fillMaxSize()) { AppRoot(tasks, habits, checkIns, preferences) }
            }
        }
    }
}
