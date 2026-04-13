package com.example.ecotrack.ui.profile.achievements

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AchievementDetailScreen(
    achievementId: Int,
    navController: NavHostController
) {
    val achievement = Achievement(
        id = achievementId,
        title = "Water Alchemist",
        iconRes = android.R.drawable.ic_menu_gallery,
        levels = listOf(
            AchievementLevel(1, "Dew Drop", "Saved 10 liters of water", "10L", android.R.drawable.ic_menu_report_image),
            AchievementLevel(2, "River Guardian", "Saved 100 liters of water", "100L", android.R.drawable.ic_menu_gallery),
            AchievementLevel(3, "Ocean Master", "Saved 1000 liters of water", "1000L", android.R.drawable.ic_menu_camera)
        )
    )

    val pagerState = rememberPagerState(pageCount = { achievement.levels.size })

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
        // Затемнение заднего фона
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.5f))
                .clickable { navController.popBackStack() }
        )

        // Основная карточка (занимает 60% экрана)
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.65f),
            shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp),
            color = Color(0xFFE0E5EC)
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                // Картинка с градиентом
                Box(modifier = Modifier.weight(0.6f)) {
                    HorizontalPager(state = pagerState, modifier = Modifier.fillMaxSize()) { page ->
                        val level = achievement.levels[page]
                        Box(modifier = Modifier.fillMaxSize()) {
                            Image(
                                painter = painterResource(level.imageRes),
                                contentDescription = null,
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )
                            Box(modifier = Modifier.fillMaxSize().background(
                                Brush.verticalGradient(listOf(Color.Transparent, Color.Black.copy(0.7f)))
                            ))
                            Column(modifier = Modifier.align(Alignment.BottomStart).padding(20.dp)) {
                                Text(
                                    text = "Level ${level.levelNumber}: ${level.title}",
                                    color = Color.White,
                                    fontSize = 22.sp,
                                    fontWeight = FontWeight.Black
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                AchievementProgressIndicator(
                                    totalLevels = achievement.levels.size,
                                    currentPage = pagerState.currentPage
                                )
                            }
                        }
                    }

                    // Кнопка закрыть
                    IconButton(
                        onClick = { navController.popBackStack() },
                        modifier = Modifier.align(Alignment.TopEnd).padding(8.dp).background(Color.Black.copy(0.3f), RoundedCornerShape(50))
                    ) {
                        Icon(Icons.Default.Close, contentDescription = null, tint = Color.White)
                    }
                }

                // Описание
                Column(
                    modifier = Modifier.weight(0.4f).padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    val currentLevel = achievement.levels[pagerState.currentPage]
                    Text(
                        text = currentLevel.description,
                        textAlign = TextAlign.Center,
                        fontSize = 16.sp,
                        color = Color(0xFF2D3748)
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        text = "Goal: ${currentLevel.goal}",
                        style = MaterialTheme.typography.labelLarge,
                        color = Color(0xFF718096)
                    )
                }
            }
        }
    }
}