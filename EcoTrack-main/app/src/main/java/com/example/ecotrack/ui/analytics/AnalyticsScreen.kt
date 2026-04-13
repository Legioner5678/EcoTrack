package com.example.ecotrack.ui.analytics

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnalyticsScreen(
    viewModel: AnalyticsViewModel = hiltViewModel()
) {
    val state by viewModel.impactState // Используем правильное имя переменной

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Impact Analytics", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color(0xFFE0E5EC))
            )
        },
        containerColor = Color(0xFFE0E5EC)
    ) { padding ->
        Column(modifier = Modifier.padding(padding).padding(16.dp)) {
            Text("Environmental Statistics", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(16.dp))

            // Используем те поля, которые реально есть в AnalyticsState
            AnalyticsCard("Total Water Saved", "${state.waterSaved} Liters")
            AnalyticsCard("CO2 Reduction", "${String.format("%.2f", state.co2Reduced)} kg")
            AnalyticsCard("Eco-Points Earned", "${state.points} pts")
        }
    }
}

@Composable
fun AnalyticsCard(label: String, value: String) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(modifier = Modifier.padding(16.dp).fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(label, color = Color.Gray)
            Text(value, fontWeight = FontWeight.Bold, color = Color(0xFF3B82F6))
        }
    }
}