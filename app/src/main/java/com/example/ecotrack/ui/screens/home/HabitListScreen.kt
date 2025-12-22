@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.ecotrack.ui.screens.home

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.ecotrack.domain.model.Habit

@Composable
fun HabitListScreen(
    viewModel: HomeViewModel = hiltViewModel()
) {
    val habits by viewModel.habits.collectAsState()

    Scaffold(
        topBar = { TopAppBar(title = { Text("Мои Эко-Привычки") }) }
    ) { paddingValues ->

        LazyColumn(contentPadding = paddingValues) {
            items(habits, key = { it.id }) { habit ->
                HabitItemCard(
                    habit = habit,
                    onToggle = { viewModel.onHabitToggled(habit) }
                )
                Divider()
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
                contentDescription = null
            )
        }
    )
}