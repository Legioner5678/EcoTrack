package com.example.ecotrack.ui.profile.achievements

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.ecotrack.viewmodel.ProfileComposeViewModel

data class Rank(val name: String, val minPoints: Int, val color: Color)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RankListScreen(navController: NavHostController, viewModel: ProfileComposeViewModel = hiltViewModel()) {
    val totalPoints by viewModel.totalPoints.collectAsState()

    val ranks = listOf(
        Rank("Eco Novice", 0, Color(0xFF718096)),
        Rank("Nature Scout", 200, Color(0xFF4CAF50)),
        Rank("Earth Guardian", 1000, Color(0xFF3B82F6)),
        Rank("Green Legend", 5000, Color(0xFFFFB300))
    )

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Eco Ranks", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color(0xFFE0E5EC))
            )
        },
        containerColor = Color(0xFFE0E5EC)
    ) { padding ->
        LazyColumn(modifier = Modifier.padding(padding).fillMaxSize().padding(16.dp)) {
            items(ranks) { rank ->
                val isUnlocked = (totalPoints ?: 0) >= rank.minPoints
                Surface(
                    modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp),
                    shape = RoundedCornerShape(16.dp),
                    color = if (isUnlocked) Color.White.copy(alpha = 0.8f) else Color.White.copy(alpha = 0.3f),
                    shadowElevation = if (isUnlocked) 4.dp else 0.dp
                ) {
                    Row(Modifier.padding(20.dp), verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            Icons.Default.CheckCircle,
                            contentDescription = null,
                            tint = if (isUnlocked) rank.color else Color.Gray
                        )
                        Spacer(Modifier.width(16.dp))
                        Column {
                            Text(rank.name, fontWeight = FontWeight.Bold, color = if (isUnlocked) Color.Black else Color.Gray)
                            Text("Required: ${rank.minPoints} pts", fontSize = 12.sp)
                        }
                    }
                }
            }
        }
    }
}