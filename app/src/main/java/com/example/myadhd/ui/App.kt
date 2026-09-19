package com.example.myadhd.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.*
import com.example.myadhd.R
import com.example.myadhd.data.TaskEntity
import com.example.myadhd.ui.viewmodel.TaskViewModel

@Composable
fun AppRoot(vm: TaskViewModel) {
    val nav = rememberNavController()
    var onboarded by remember { mutableStateOf(false) }
    if (!onboarded) { Onboarding { onboarded = true } ; return }
    Scaffold(bottomBar = { NavigationBar {
        listOf("home" to R.string.home, "tasks" to R.string.tasks, "focus" to R.string.focus, "profile" to R.string.profile).forEach { (route, label) ->
            NavigationBarItem(selected = false, onClick = { nav.navigate(route) }, icon = {}, label = { Text(stringResource(label)) })
        }
    }}) { pad -> NavHost(nav, startDestination = "home", Modifier.padding(pad)) {
        composable("home") { HomeScreen(vm.tasks.collectAsState().value, onTasks = { nav.navigate("tasks") }) }
        composable("tasks") { TasksScreen(vm) }
        composable("focus") { FocusScreen() }
        composable("profile") { ProfileScreen() }
    }}
}

@Composable private fun Onboarding(done: () -> Unit) {
    Column(Modifier.fillMaxSize().padding(24.dp), verticalArrangement = Arrangement.Center) {
        Text(stringResource(R.string.welcome_title), style = MaterialTheme.typography.headlineLarge)
        Spacer(Modifier.height(12.dp))
        Text(stringResource(R.string.welcome_subtitle), style = MaterialTheme.typography.bodyLarge)
        Spacer(Modifier.height(28.dp))
        Button(onClick = done, modifier = Modifier.fillMaxWidth()) { Text(stringResource(R.string.get_started)) }
        TextButton(onClick = done, modifier = Modifier.fillMaxWidth()) { Text(stringResource(R.string.continue_guest)) }
    }
}

@Composable private fun HomeScreen(tasks: List<TaskEntity>, onTasks: () -> Unit) {
    Column(Modifier.fillMaxSize().padding(24.dp)) {
        Text(stringResource(R.string.today), style = MaterialTheme.typography.headlineMedium)
        Text(stringResource(R.string.next_step), Modifier.padding(top = 8.dp))
        val next = tasks.firstOrNull { !it.completed }
        Card(Modifier.fillMaxWidth().padding(top = 20.dp)) { Column(Modifier.padding(20.dp)) { Text(next?.title ?: stringResource(R.string.no_tasks)); if (next != null) Text("${next.estimatedMinutes} min") } }
        Button(onClick = onTasks, Modifier.padding(top = 20.dp)) { Text(stringResource(R.string.view_tasks)) }
    }
}

@Composable private fun TasksScreen(vm: TaskViewModel) {
    var title by remember { mutableStateOf("") }
    val tasks = vm.tasks.collectAsState().value
    Column(Modifier.fillMaxSize().padding(20.dp)) {
        Text(stringResource(R.string.tasks), style = MaterialTheme.typography.headlineMedium)
        Row(Modifier.fillMaxWidth().padding(vertical = 16.dp)) { OutlinedTextField(title, { title = it }, Modifier.weight(1f), placeholder = { Text(stringResource(R.string.task_hint)) }, singleLine = true); Spacer(Modifier.width(8.dp)); Button(onClick = { vm.add(title); title = "" }) { Text(stringResource(R.string.add)) } }
        tasks.forEach { task -> ListItem(headlineContent = { Text(task.title) }, supportingContent = { Text("${task.estimatedMinutes} min") }, leadingContent = { Checkbox(task.completed, { vm.toggle(task) }) }, trailingContent = { TextButton({ vm.delete(task) }) { Text(stringResource(R.string.delete)) } }) }
    }
}

@Composable private fun FocusScreen() {
    var seconds by remember { mutableIntStateOf(15 * 60) }
    var running by remember { mutableStateOf(false) }
    LaunchedEffect(running) { while (running && seconds > 0) { kotlinx.coroutines.delay(1000); seconds-- }; if (seconds == 0) running = false }
    Column(Modifier.fillMaxSize().padding(24.dp), verticalArrangement = Arrangement.Center) {
        Text(stringResource(R.string.focus), style = MaterialTheme.typography.headlineMedium)
        Text(String.format("%02d:%02d", seconds / 60, seconds % 60), style = MaterialTheme.typography.displayLarge, modifier = Modifier.padding(vertical = 28.dp))
        Row { Button({ running = !running }) { Text(if (running) stringResource(R.string.pause) else stringResource(R.string.start)) }; Spacer(Modifier.width(8.dp)); OutlinedButton({ seconds = 15 * 60; running = false }) { Text(stringResource(R.string.reset)) } }
    }
}

@Composable private fun ProfileScreen() { Column(Modifier.fillMaxSize().padding(24.dp)) { Text(stringResource(R.string.profile), style = MaterialTheme.typography.headlineMedium); Text(stringResource(R.string.disclaimer), Modifier.padding(top = 16.dp)) } }
