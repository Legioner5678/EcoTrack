package com.example.ecotrack

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.getValue
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.ecotrack.ui.theme.EcoTrackTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint // ОБЯЗАТЕЛЬНО для Hilt
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EcoTrackTheme {
                val navController = rememberNavController()

                Scaffold(
                    bottomBar = {
                        NavigationBar {
                            val navBackStackEntry by navController.currentBackStackEntryAsState()
                            val currentRoute = navBackStackEntry?.destination?.route

                            // Кнопка Главная
                            NavigationBarItem(
                                selected = currentRoute == Screen.Home.route,
                                onClick = { navController.navigate(Screen.Home.route) },
                                icon = { Icon(Icons.Default.Home, "Home") },
                                label = { Text("Home") }
                            )
                            // Кнопка Профиль (Ваш новый экран!)
                            NavigationBarItem(
                                selected = currentRoute == Screen.Profile.route,
                                onClick = { navController.navigate(Screen.Profile.route) },
                                icon = { Icon(Icons.Default.Person, "Profile") },
                                label = { Text("Profile") }
                            )
                            // Кнопка Настройки
                            NavigationBarItem(
                                selected = currentRoute == Screen.Settings.route,
                                onClick = { navController.navigate(Screen.Settings.route) },
                                icon = { Icon(Icons.Default.Settings, "Settings") },
                                label = { Text("Settings") }
                            )
                        }
                    }
                ) { innerPadding ->
                    // Передаем отступы, чтобы контент не перекрывался менюшкой
                    NavigationGraph(navController = navController, modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}
