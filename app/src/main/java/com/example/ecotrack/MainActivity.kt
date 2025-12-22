package com.example.ecotrack

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.ecotrack.ui.theme.EcoTrackTheme
import dagger.hilt.android.AndroidEntryPoint
import androidx.compose.ui.unit.dp
import com.example.ecotrack.ui.profile.ProfileScreen
import com.example.ecotrack.ui.screens.home.HabitListScreen
import com.example.ecotrack.ui.analytics.AnalyticsScreen
@AndroidEntryPoint // Внедрение зависимостей в Activity
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EcoTrackTheme {
                val navController = rememberNavController()

                Scaffold(
                    bottomBar = {
                        BottomNavigationBar(navController = navController)
                    }
                ) { innerPadding ->
                    NavigationGraph(
                        navController = navController,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
                EcoTrackAppEntryPoint()
            }
        }
    }
}

// Главный контейнер для навигации
@Composable
fun EcoTrackAppEntryPoint() {
    // Создаем контроллер навигации
    val navController = rememberNavController()

    Scaffold(
        // Нижнее меню - задача Данияра
        bottomBar = {
            // Здесь Данияр добавит BottomAppBar
        }
    ) { paddingValues ->

        // Навигационный граф (NavHost)
        NavHost(
            navController = navController,
            startDestination = "profile", // Экран Алена
            modifier = Modifier.padding(paddingValues)
        ) {
            // Маршрут для экрана Алена
            composable("home") {
                HabitListScreen()
            }
            // Маршрут для экрана статистики
            composable("statistics") {
                PlaceholderScreen("Statistics")
            }
            // Маршрут для экрана профиля Дастана
            composable("profile") {
                ProfileScreen()
            }
            // Маршрут для экрана Аналитики
            composable(route= "analytics") {
                AnalyticsScreen()
            }

            // Маршрут для Retrofit-советов
            composable("tips") {
                PlaceholderScreen("Eco Tips (Retrofit)")
            }
        }
    }
}

// Заглушка, чтобы проверить работу навигации
@Composable
fun PlaceholderScreen(name: String) {
    Text(text = "Welcome to $name Screen", modifier = Modifier.padding(16.dp))
}

// Добавь этот импорт в начало MainActivity.kt, если его нет
// import androidx.compose.ui.unit.dp