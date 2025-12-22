package com.example.ecotrack.ui.profile

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.ecotrack.domain.model.EcoPointEntity
import com.example.ecotrack.viewmodel.ProfileComposeViewModel
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun ProfileScreen(viewModel: ProfileComposeViewModel = hiltViewModel()) {
    val context = LocalContext.current

    val userName by viewModel.userName.collectAsState()
    val habits by viewModel.habits.collectAsState()
    val ecoPoints by viewModel.ecoPoints.collectAsState()

    var nameInput by remember { mutableStateOf(userName) }
    var habitInput by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Hello $userName", style = MaterialTheme.typography.headlineSmall)
        Text("Eco Points: $ecoPoints", style = MaterialTheme.typography.bodyLarge)

        OutlinedTextField(
            value = nameInput,
            onValueChange = { nameInput = it },
            label = { Text("Enter your name") },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                if (nameInput.isNotEmpty()) {
                    viewModel.saveName(nameInput)
                    Toast.makeText(context, "Name saved", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) { Text("Save Name") }

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = habitInput,
            onValueChange = { habitInput = it },
            label = { Text("New Habit") },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                if (habitInput.isNotEmpty()) {
                    viewModel.addHabit(habitInput)
                    habitInput = ""
                    Toast.makeText(context, "+1 Eco Point added!", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) { Text("Add Habit") }

        Spacer(modifier = Modifier.height(12.dp))
        Text("Your Habits:", style = MaterialTheme.typography.titleMedium)

        LazyColumn {
            items(habits) { habit ->
                HabitItem(habit = habit, onDelete = { viewModel.deleteHabit(it) })
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { viewModel.resetHabits() },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
        ) { Text("Reset All Habits") }
    }
}

@Composable
fun HabitItem(habit: EcoPointEntity, onDelete: (EcoPointEntity) -> Unit) {
    val sdf = SimpleDateFormat("dd MMM yyyy HH:mm", Locale.getDefault())
    val dateString = sdf.format(Date(habit.date))
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text("${habit.title} (+${habit.points}) - $dateString")
        TextButton(onClick = { onDelete(habit) }) {
            Text("Delete")
        }
    }
}
