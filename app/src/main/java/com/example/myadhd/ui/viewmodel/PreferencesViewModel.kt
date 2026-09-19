package com.example.myadhd.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.myadhd.data.PreferencesRepository
import com.example.myadhd.data.UserPreferences
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class PreferencesViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = PreferencesRepository(application)
    val preferences: StateFlow<UserPreferences> = repository.preferences.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), UserPreferences())
    fun completeOnboarding() = viewModelScope.launch { repository.setOnboardingComplete(true) }
    fun setLanguage(language: String) = viewModelScope.launch { repository.setLanguage(language) }
    fun setDarkMode(enabled: Boolean) = viewModelScope.launch { repository.setDarkMode(enabled) }
}
