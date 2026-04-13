package com.example.ecotrack.ui.profile.achievements

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

// Модели данных
data class AchievementLevel(
    val levelNumber: Int,
    val title: String,
    val description: String,
    val goal: String,
    val imageRes: Int,
    val completionDate: String? = null
)

data class Achievement(
    val id: Int,
    val title: String,
    val iconRes: Int,
    val levels: List<AchievementLevel>
)

// Твой компонент "Черточки-индикаторы"
@Composable
fun AchievementProgressIndicator(
    totalLevels: Int,
    currentPage: Int,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        repeat(totalLevels) { index ->
            Box(
                modifier = Modifier
                    .width(24.dp)
                    .height(4.dp)
                    .background(
                        if (currentPage == index) Color.White else Color.White.copy(0.3f),
                        RoundedCornerShape(2.dp)
                    )
            )
        }
    }
}