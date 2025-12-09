package com.example.ecotrack.ui.profile

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ecotrack.viewmodel.ProfileComposeViewModel

@Composable
fun ProfileScreen() {

    val viewModel: ProfileComposeViewModel = viewModel()
    val context = LocalContext.current

    var nameInput by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        Text(
            text = viewModel.userName.value,
            style = MaterialTheme.typography.headlineSmall
        )

        Text(
            text = "Completed habits: ${viewModel.habitsCount.value}",
            style = MaterialTheme.typography.bodyLarge
        )

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
        ) {
            Text("Save Name")
        }

        Button(
            onClick = {
                viewModel.resetProgress()
                Toast.makeText(context, "Progress reset", Toast.LENGTH_SHORT).show()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Reset Progress")
        }

        // ✅ Тестовая кнопка (можно потом удалить)
        OutlinedButton(
            onClick = {
                viewModel.addTestHabit()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("TEST: Add Habit +1")
        }
    }
}
