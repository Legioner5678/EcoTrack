@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.ecotrack.ui.screens.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.ecotrack.domain.model.Habit

@Composable
fun HabitListScreen(
    viewModel: HomeViewModel = hiltViewModel()
) {
    val habits by viewModel.habits.collectAsState()
    val aqiData by viewModel.aqiData.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("EcoTrack: Главная") })
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = paddingValues
        ) {
            // Секция с API данными
            item {
                if (aqiData != null) {
                    Card(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer
                        )
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = "📍 ${aqiData!!.city.name}",
                                style = MaterialTheme.typography.titleMedium
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "AQI: ${aqiData!!.aqi}",
                                style = MaterialTheme.typography.headlineMedium
                            )
                            Text(
                                text = if (aqiData!!.aqi < 50) "Воздух чистый 🌿" else "Загрязнение воздуха ⚠️",
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                }
            }

            // Заголовок списка
            item {
                Text(
                    text = "Ваши эко-цели:",
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.titleSmall
                )
            }

            // Список привычек
            items(habits, key = { it.id }) { habit ->
                HabitItemCard(
                    habit = habit,
                    onToggle = { viewModel.onHabitToggled(habit) }
                )
                HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))
            }
        }
    }
}

@Composable
fun HabitItemCard(
    habit: Habit,
    onToggle: () -> Unit
) {
    ListItem(
        modifier = Modifier.padding(horizontal = 8.dp),
        headlineContent = { Text(habit.title) },
        supportingContent = { Text(habit.description) },
        trailingContent = {
            Checkbox(
                checked = habit.isCompletedToday,
                onCheckedChange = { onToggle() }
            )
        },
        leadingContent = {
            Icon(
                imageVector = if (habit.isCompletedToday)
                    Icons.Default.CheckCircle else Icons.Default.Circle,
                contentDescription = null,
                tint = if (habit.isCompletedToday)
                    MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline
            )
        }
    )
}