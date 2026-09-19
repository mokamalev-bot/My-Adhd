package com.example.myadhd.data

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.appDataStore by preferencesDataStore(name = "my_adhd_preferences")

data class UserPreferences(val onboardingComplete: Boolean = false, val language: String = "system", val darkMode: Boolean = false)

class PreferencesRepository(private val context: Context) {
    private object Keys {
        val onboardingComplete = booleanPreferencesKey("onboarding_complete")
        val language = stringPreferencesKey("language")
        val darkMode = booleanPreferencesKey("dark_mode")
    }
    val preferences: Flow<UserPreferences> = context.appDataStore.data.map { values ->
        UserPreferences(values[Keys.onboardingComplete] ?: false, values[Keys.language] ?: "system", values[Keys.darkMode] ?: false)
    }
    suspend fun setOnboardingComplete(value: Boolean) = context.appDataStore.edit { it[Keys.onboardingComplete] = value }
    suspend fun setLanguage(value: String) = context.appDataStore.edit { it[Keys.language] = value }
    suspend fun setDarkMode(value: Boolean) = context.appDataStore.edit { it[Keys.darkMode] = value }
}
