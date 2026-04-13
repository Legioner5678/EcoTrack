package com.example.ecotrack.ui.profile.shop

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ShoppingCart
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

data class ShopItem(val name: String, val price: Int, val desc: String)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShopScreen(navController: NavHostController, viewModel: ProfileComposeViewModel = hiltViewModel()) {
    val totalPoints by viewModel.totalPoints.collectAsState()

    val items = listOf(
        ShopItem("Eco Sticker Pack", 100, "Digital badges for your profile"),
        ShopItem("Dark Green Theme", 500, "Exclusive app interface skin"),
        ShopItem("Tree Certificate", 1500, "We plant a real tree in your name"),
        ShopItem("Premium Map Icons", 300, "New icons for eco-points on map")
    )

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Eco Shop", fontWeight = FontWeight.Bold) },
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
            item {
                Surface(
                    modifier = Modifier.fillMaxWidth().padding(bottom = 20.dp),
                    color = Color(0xFF3B82F6),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Row(Modifier.padding(20.dp), verticalAlignment = Alignment.CenterVertically) {
                        Text("Your Balance:", color = Color.White)
                        Spacer(Modifier.weight(1f))
                        Text("$totalPoints Points", color = Color.White, fontWeight = FontWeight.Black, fontSize = 20.sp)
                    }
                }
            }

            items(items) { shopItem ->
                Card(
                    modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.5f)),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Row(Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                        Column(Modifier.weight(1f)) {
                            Text(shopItem.name, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                            Text(shopItem.desc, fontSize = 12.sp, color = Color.Gray)
                        }
                        Button(
                            onClick = { /* Логика покупки */ },
                            enabled = (totalPoints ?: 0) >= shopItem.price,
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50))
                        ) {
                            Text("${shopItem.price} pts")
                        }
                    }
                }
            }
        }
    }
}