package com.example.ecotrack

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

import com.example.ecotrack.ui.auth.AuthScreen
import com.example.ecotrack.ui.screens.news.NewsScreen
import com.example.ecotrack.ui.habits.HabitTrackerScreen
import com.example.ecotrack.ui.map.EcoMapScreen
import com.example.ecotrack.ui.screens.ChatScreen
import com.example.ecotrack.ui.profile.ProfileScreen
import com.example.ecotrack.ui.profile.settings.SettingsScreen
import com.example.ecotrack.ui.profile.shop.ShopScreen
import com.example.ecotrack.ui.profile.achievements.AchievementListScreen
import com.example.ecotrack.ui.profile.achievements.AchievementDetailScreen
import com.example.ecotrack.ui.profile.achievements.RankListScreen

sealed class Screen(val route: String) {
    data object Auth : Screen("auth")
    data object News : Screen("home")
    data object Habits : Screen("habits")
    data object Profile : Screen("profile")
    data object Settings : Screen("settings")
    data object Chat : Screen("chat")
    data object Map : Screen("map")
    data object Achievements : Screen("achievements")
    data object Shop : Screen("shop")
    data object Ranks : Screen("ranks")
}

@Composable
fun NavigationGraph(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(
        navController = navController,
        startDestination = Screen.Auth.route,
        modifier = modifier
    ) {
        composable(Screen.Auth.route) {
            AuthScreen(onLoginSuccess = {
                navController.navigate(Screen.News.route) {
                    popUpTo(Screen.Auth.route) { inclusive = true }
                }
            })
        }
        composable(Screen.News.route) { NewsScreen(navController) }
        composable(Screen.Habits.route) { HabitTrackerScreen() }
        composable(Screen.Map.route) { EcoMapScreen() }
        composable(Screen.Chat.route) { ChatScreen() }
        composable(Screen.Profile.route) { ProfileScreen(navController = navController) }
        composable(Screen.Settings.route) { SettingsScreen(navController = navController) }
        composable(Screen.Shop.route) { ShopScreen(navController = navController) }
        composable(Screen.Ranks.route) { RankListScreen(navController = navController) }
        composable(Screen.Achievements.route) { AchievementListScreen(navController = navController) }
        composable(
            route = "achievement_detail/{achievementId}",
            arguments = listOf(navArgument("achievementId") { type = NavType.IntType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("achievementId") ?: 1
            AchievementDetailScreen(achievementId = id, navController = navController)
        }
    }
}