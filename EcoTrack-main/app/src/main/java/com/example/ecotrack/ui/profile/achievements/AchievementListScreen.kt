package com.example.ecotrack.ui.profile.achievements

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AchievementListScreen(navController: NavHostController) {
    // Временные данные для теста
    val achievementTitles = listOf("Eco-Warrior Path", "H2O Alchemist", "Urban Nomad")

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("My Achievements", fontWeight = FontWeight.ExtraBold) },
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
        LazyColumn(
            modifier = Modifier.padding(padding).fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(achievementTitles) { title ->
                Surface(
                    modifier = Modifier.fillMaxWidth().clickable {
                        // Пока передаем ID 1 для теста
                        navController.navigate("achievement_detail/1")
                    },
                    shape = RoundedCornerShape(16.dp),
                    color = Color(0xFFE0E5EC),
                    shadowElevation = 4.dp
                ) {
                    Row(Modifier.padding(20.dp), verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Star, contentDescription = null, tint = Color(0xFF3B82F6))
                        Spacer(modifier = Modifier.width(16.dp))
                        Text(title, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    }
                }
            }
        }
    }
}