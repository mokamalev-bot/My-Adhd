package com.example.myadhd.ui

import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.os.LocaleListCompat
import androidx.navigation.compose.*
import com.example.myadhd.R
import com.example.myadhd.data.TaskEntity
import com.example.myadhd.ui.viewmodel.PreferencesViewModel
import com.example.myadhd.ui.viewmodel.TaskViewModel

@Composable
fun AppRoot(taskVm: TaskViewModel, preferencesVm: PreferencesViewModel) {
    val preferences by preferencesVm.preferences.collectAsState()
    if (!preferences.onboardingComplete) {
        Onboarding({ preferencesVm.completeOnboarding() }) { language -> preferencesVm.setLanguage(language); applyLanguage(language) }
        return
    }
    val nav = rememberNavController()
    val backStack by nav.currentBackStackEntryAsState()
    val currentRoute = backStack?.destination?.route
    val destinations = listOf("home" to R.string.home, "tasks" to R.string.tasks, "focus" to R.string.focus, "profile" to R.string.profile)
    Scaffold(bottomBar = { NavigationBar { destinations.forEach { (route, label) -> NavigationBarItem(currentRoute == route, { nav.navigate(route) { launchSingleTop = true } }, icon = {}, label = { Text(stringResource(label)) }) } } }) { padding ->
        NavHost(nav, "home", Modifier.padding(padding)) {
            composable("home") { HomeScreen(taskVm.tasks.collectAsState().value) { nav.navigate("tasks") } }
            composable("tasks") { TasksScreen(taskVm) }
            composable("focus") { FocusScreen() }
            composable("profile") { ProfileScreen(preferences.darkMode, preferences.language, preferencesVm::setDarkMode) { language -> preferencesVm.setLanguage(language); applyLanguage(language) } }
        }
    }
}

private fun applyLanguage(language: String) { AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags(if (language == "system") "" else language)) }

@Composable
private fun Onboarding(onComplete: () -> Unit, onLanguage: (String) -> Unit) {
    var language by remember { mutableStateOf("system") }
    Column(Modifier.fillMaxSize().padding(24.dp), verticalArrangement = Arrangement.Center) {
        Text(stringResource(R.string.welcome_title), style = MaterialTheme.typography.headlineLarge)
        Spacer(Modifier.height(12.dp)); Text(stringResource(R.string.welcome_subtitle), style = MaterialTheme.typography.bodyLarge)
        Spacer(Modifier.height(24.dp)); Text(stringResource(R.string.language), style = MaterialTheme.typography.titleMedium)
        Row(Modifier.padding(vertical = 8.dp)) {
            FilterChip(language == "system", { language = "system" }, label = { Text(stringResource(R.string.language_system)) }); Spacer(Modifier.width(8.dp))
            FilterChip(language == "en", { language = "en" }, label = { Text(stringResource(R.string.language_english)) }); Spacer(Modifier.width(8.dp))
            FilterChip(language == "ar", { language = "ar" }, label = { Text(stringResource(R.string.language_arabic)) })
        }
        Button({ onLanguage(language); onComplete() }, Modifier.fillMaxWidth()) { Text(stringResource(R.string.get_started)) }
        TextButton({ onLanguage(language); onComplete() }, Modifier.fillMaxWidth()) { Text(stringResource(R.string.continue_guest)) }
    }
}

@Composable
private fun HomeScreen(tasks: List<TaskEntity>, onTasks: () -> Unit) {
    Column(Modifier.fillMaxSize().padding(24.dp)) {
        Text(stringResource(R.string.today), style = MaterialTheme.typography.headlineMedium); Text(stringResource(R.string.next_step), Modifier.padding(top = 8.dp))
        val next = tasks.firstOrNull { !it.completed }
        Card(Modifier.fillMaxWidth().padding(top = 20.dp)) { Column(Modifier.padding(20.dp)) { Text(next?.title ?: stringResource(R.string.no_tasks)); next?.let { Text(stringResource(R.string.minutes_format, it.estimatedMinutes)) } } }
        Button(onTasks, Modifier.padding(top = 20.dp)) { Text(stringResource(R.string.view_tasks)) }
    }
}

@Composable
private fun TasksScreen(vm: TaskViewModel) {
    var title by remember { mutableStateOf("") }; val tasks = vm.tasks.collectAsState().value
    Column(Modifier.fillMaxSize().padding(20.dp)) {
        Text(stringResource(R.string.tasks), style = MaterialTheme.typography.headlineMedium)
        Row(Modifier.fillMaxWidth().padding(vertical = 16.dp)) { OutlinedTextField(title, { title = it }, Modifier.weight(1f), placeholder = { Text(stringResource(R.string.task_hint)) }, singleLine = true); Spacer(Modifier.width(8.dp)); Button({ vm.add(title); title = "" }) { Text(stringResource(R.string.add)) } }
        tasks.forEach { task -> ListItem(headlineContent = { Text(task.title) }, supportingContent = { Text(stringResource(R.string.minutes_format, task.estimatedMinutes)) }, leadingContent = { Checkbox(task.completed) { vm.toggle(task) } }, trailingContent = { TextButton({ vm.delete(task) }) { Text(stringResource(R.string.delete)) } }) }
    }
}

@Composable
private fun FocusScreen() {
    var selectedMinutes by remember { mutableIntStateOf(15) }; var seconds by remember { mutableIntStateOf(15 * 60) }; var running by remember { mutableStateOf(false) }
    LaunchedEffect(running) { while (running && seconds > 0) { kotlinx.coroutines.delay(1000); seconds-- }; if (seconds == 0) running = false }
    Column(Modifier.fillMaxSize().padding(24.dp), verticalArrangement = Arrangement.Center) {
        Text(stringResource(R.string.focus), style = MaterialTheme.typography.headlineMedium); Text(String.format("%02d:%02d", seconds / 60, seconds % 60), style = MaterialTheme.typography.displayLarge, Modifier.padding(vertical = 28.dp))
        Row { listOf(5, 10, 15, 25).forEach { minutes -> FilterChip(selectedMinutes == minutes, { selectedMinutes = minutes; seconds = minutes * 60; running = false }, label = { Text("$minutes") }); Spacer(Modifier.width(4.dp)) } }
        Row(Modifier.padding(top = 16.dp)) { Button({ running = !running }) { Text(if (running) stringResource(R.string.pause) else stringResource(R.string.start)) }; Spacer(Modifier.width(8.dp)); OutlinedButton({ seconds = selectedMinutes * 60; running = false }) { Text(stringResource(R.string.reset)) } }
    }
}

@Composable
private fun ProfileScreen(darkMode: Boolean, language: String, onDarkModeChanged: (Boolean) -> Unit, onLanguageChanged: (String) -> Unit) {
    Column(Modifier.fillMaxSize().padding(24.dp)) {
        Text(stringResource(R.string.profile), style = MaterialTheme.typography.headlineMedium)
        Row(Modifier.fillMaxWidth().padding(top = 20.dp), horizontalArrangement = Arrangement.SpaceBetween) { Text(stringResource(R.string.dark_mode)); Switch(darkMode, onDarkModeChanged) }
        Text(stringResource(R.string.language), Modifier.padding(top = 20.dp), style = MaterialTheme.typography.titleMedium)
        Row(Modifier.padding(top = 8.dp)) { FilterChip(language == "en", { onLanguageChanged("en") }, label = { Text(stringResource(R.string.language_english)) }); Spacer(Modifier.width(8.dp)); FilterChip(language == "ar", { onLanguageChanged("ar") }, label = { Text(stringResource(R.string.language_arabic)) }); Spacer(Modifier.width(8.dp)); FilterChip(language == "system", { onLanguageChanged("system") }, label = { Text(stringResource(R.string.language_system)) }) }
        Text(stringResource(R.string.disclaimer), Modifier.padding(top = 24.dp))
    }
}
