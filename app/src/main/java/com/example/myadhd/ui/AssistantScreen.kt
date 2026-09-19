package com.example.myadhd.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.myadhd.R
import com.example.myadhd.ai.SafeLocalAiGateway
import kotlinx.coroutines.launch

@Composable
fun AssistantScreen() {
    var message by remember { mutableStateOf("") }
    var response by remember { mutableStateOf<String?>(null) }
    val scope = rememberCoroutineScope()
    val gateway = remember { SafeLocalAiGateway() }
    Column(Modifier.fillMaxSize().padding(20.dp)) {
        Text(stringResource(R.string.assistant), style = MaterialTheme.typography.headlineMedium)
        Text(stringResource(R.string.assistant_disclaimer), Modifier.padding(top = 8.dp))
        Spacer(Modifier.height(16.dp))
        OutlinedTextField(message, { message = it }, Modifier.fillMaxWidth(), label = { Text(stringResource(R.string.assistant_hint)) })
        Button(enabled = message.isNotBlank(), onClick = { scope.launch { response = gateway.reply(message).text } }, Modifier.padding(top = 12.dp)) { Text(stringResource(R.string.send)) }
        response?.let { Card(Modifier.fillMaxWidth().padding(top = 20.dp)) { Text(it, Modifier.padding(16.dp)) } }
    }
}
