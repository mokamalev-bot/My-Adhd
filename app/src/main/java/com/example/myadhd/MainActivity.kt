package com.example.myadhd

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myadhd.ui.theme.MyAdhdTheme
import com.example.myadhd.ui.viewmodel.TaskViewModel
import com.example.myadhd.ui.AppRoot

class MainActivity : ComponentActivity() {
    override fun onCreate(state: Bundle?) {
        super.onCreate(state)
        enableEdgeToEdge()
        setContent {
            MyAdhdTheme { Surface(Modifier.fillMaxSize()) { AppRoot(viewModel()) } }
        }
    }
}
