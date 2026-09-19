package com.example.myadhd.ui.theme

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val Light = lightColorScheme(primary = Color(0xFF5367D9), secondary = Color(0xFF4D8C7A), background = Color(0xFFF7F8FC))
private val Dark = darkColorScheme(primary = Color(0xFFB8C1FF), secondary = Color(0xFF9DD7C4))

@Composable
fun MyAdhdTheme(darkTheme: Boolean = false, content: @Composable () -> Unit) {
    MaterialTheme(if (darkTheme) Dark else Light, content = content)
}
