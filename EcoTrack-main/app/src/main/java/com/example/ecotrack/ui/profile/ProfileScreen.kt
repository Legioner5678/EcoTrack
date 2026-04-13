package com.example.ecotrack.ui.profile

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.rounded.Person
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    navController: NavHostController,
    viewModel: ProfileComposeViewModel = hiltViewModel()
) {
    val totalPoints by viewModel.totalPoints.collectAsState(initial = 0)
    val user by viewModel.currentUser.collectAsState()

    // Логика определения ранга
    val (rankName, rankColor) = when {
        (totalPoints ?: 0) >= 5000 -> "Green Legend" to Color(0xFFFFB300)
        (totalPoints ?: 0) >= 1000 -> "Earth Guardian" to Color(0xFF3B82F6)
        (totalPoints ?: 0) >= 200 -> "Nature Scout" to Color(0xFF4CAF50)
        else -> "Eco Novice" to Color(0xFF718096)
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Eco Profile", fontWeight = FontWeight.ExtraBold) },
                actions = {
                    IconButton(onClick = { navController.navigate("settings") }) {
                        Icon(Icons.Default.Settings, contentDescription = "Settings", tint = Color(0xFF718096))
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color(0xFFE0E5EC))
            )
        },
        containerColor = Color(0xFFE0E5EC)
    ) { padding ->
        Column(
            modifier = Modifier.padding(padding).fillMaxSize().padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                color = Color(0xFFE0E5EC),
                shadowElevation = 8.dp
            ) {
                Row(modifier = Modifier.padding(20.dp), verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Rounded.Person, contentDescription = null, modifier = Modifier.size(60.dp), tint = Color(0xFF3B82F6))
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        Text(text = user?.name ?: "Eco Hero", style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold))
                        Text(
                            text = "Eco Rank: $rankName",
                            color = rankColor,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.clickable { navController.navigate("ranks") }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Surface(
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(16.dp),
                    color = Color(0xFFE0E5EC),
                    shadowElevation = 4.dp
                ) {
                    Column(Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Eco Points", fontSize = 12.sp, color = Color(0xFF718096))
                        Text("${totalPoints ?: 0}", fontSize = 24.sp, fontWeight = FontWeight.Black, color = Color(0xFF4CAF50))
                    }
                }

                Surface(
                    modifier = Modifier.weight(1f).clickable { navController.navigate("shop") },
                    shape = RoundedCornerShape(16.dp),
                    color = Color(0xFF3B82F6),
                    shadowElevation = 4.dp
                ) {
                    Column(Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(Icons.Default.ShoppingCart, contentDescription = null, tint = Color.White)
                        Text("Shop", color = Color.White, fontWeight = FontWeight.Bold)
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Surface(
                modifier = Modifier.fillMaxWidth().clickable { navController.navigate("achievements") },
                shape = RoundedCornerShape(20.dp),
                color = Color(0xFFE0E5EC),
                shadowElevation = 4.dp
            ) {
                Row(Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Star, contentDescription = null, tint = Color(0xFFFFB300))
                    Spacer(modifier = Modifier.width(12.dp))
                    Text("View Achievements", fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.weight(1f))
                    Text("Check Progress", color = Color(0xFF718096), fontSize = 12.sp)
                }
            }
        }
    }
}