package com.example.ecotrack

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.ecotrack.ui.screens.home.HabitListScreen
import com.example.ecotrack.ui.profile.ProfileScreen
import com.example.ecotrack.ui.settings.SettingsScreen
import com.example.ecotrack.ui.map.EcoMapScreen // Импортируй созданный ранее экран

sealed class Screen(val route: String) {
    data object Home : Screen("home")
    data object Profile : Screen("profile")
    data object Settings : Screen("settings")
    data object Chat : Screen("chat")
    data object Map : Screen("map") // Добавили карту
}

@Composable
fun NavigationGraph(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
        modifier = modifier
    ) {
        composable(Screen.Home.route) {
            HabitListScreen(navController = navController)
        }
        composable(Screen.Profile.route) {
            ProfileScreen()
        }
        composable(Screen.Settings.route) {
            SettingsScreen()
        }
        composable(Screen.Chat.route) {
            com.example.ecotrack.ui.screens.ChatScreen()
        }
        composable(Screen.Map.route) {
            EcoMapScreen() // Тот самый экран с Google Maps
        }
    }
}