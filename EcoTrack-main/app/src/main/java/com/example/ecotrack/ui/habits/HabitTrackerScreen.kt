package com.example.ecotrack.ui.habits

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.DeleteOutline
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.ecotrack.domain.model.Habit
import com.example.ecotrack.ui.analytics.AnalyticsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HabitTrackerScreen(
    viewModel: HabitViewModel = hiltViewModel(),
    analyticsViewModel: AnalyticsViewModel = hiltViewModel()
) {
    val habits by viewModel.habits.collectAsState()
    val impact by analyticsViewModel.impactState

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "Habit Tracker",
                        style = MaterialTheme.typography.headlineSmall.copy(
                            fontWeight = FontWeight.ExtraBold,
                            letterSpacing = 0.5.sp
                        )
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color(0xFFE0E5EC)
                )
            )
        },
        containerColor = Color(0xFFE0E5EC)
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(24.dp),
                color = Color(0xFFE0E5EC),
                shadowElevation = 6.dp
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text(
                        "Your Ecological Impact",
                        style = MaterialTheme.typography.titleSmall,
                        color = Color(0xFF718096)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        ImpactStatItem("💧 ${impact.waterSaved}L", "Water Saved")
                        ImpactStatItem("☁️ ${String.format("%.1f", impact.co2Reduced)}kg", "CO2 Reduced")
                        ImpactStatItem("⭐ ${impact.points}", "Eco-Points")
                    }
                }
            }

            Text(
                text = "Daily Tasks",
                modifier = Modifier.padding(start = 20.dp, bottom = 8.dp),
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = Color(0xFF2D3748)
            )

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(habits) { habit ->
                    NeumorphicHabitCard(
                        habit = habit,
                        onIncrement = { viewModel.incrementHabit(habit) }
                    )
                }
            }
        }
    }
}

@Composable
fun ImpactStatItem(value: String, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(value, fontSize = 18.sp, fontWeight = FontWeight.ExtraBold, color = Color(0xFF3B82F6))
        Text(label, fontSize = 10.sp, color = Color(0xFF718096))
    }
}

@Composable
fun NeumorphicHabitCard(
    habit: Habit,
    onIncrement: () -> Unit
) {
    val targetProgress = remember(habit.currentProgress) {
        if (habit.targetCount > 0) {
            (habit.currentProgress.toFloat() / habit.targetCount.toFloat()).coerceIn(0f, 1f)
        } else 0f
    }

    val animatedProgress by animateFloatAsState(
        targetValue = targetProgress,
        animationSpec = tween(durationMillis = 600),
        label = "HabitProgress"
    )

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { if (habit.currentProgress < habit.targetCount) onIncrement() },
        shape = RoundedCornerShape(28.dp),
        color = Color(0xFFE0E5EC),
        shadowElevation = 8.dp
    ) {
        Row(
            modifier = Modifier.padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(contentAlignment = Alignment.Center) {
                CircularProgressIndicator(
                    progress = { animatedProgress },
                    modifier = Modifier.size(60.dp),
                    strokeWidth = 6.dp,
                    color = if (animatedProgress >= 1f) Color(0xFF4CAF50) else Color(0xFF3B82F6),
                    trackColor = Color(0xFFD1D9E6),
                )
                Icon(
                    imageVector = getIconForCategory(habit.category),
                    contentDescription = null,
                    modifier = Modifier.size(26.dp),
                    tint = if (animatedProgress >= 1f) Color(0xFF4CAF50) else Color(0xFF718096)
                )
            }

            Spacer(modifier = Modifier.width(20.dp))

            Column(modifier = Modifier.weight(1f)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = habit.name,
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = Color(0xFF2D3748),
                        modifier = Modifier.weight(1f)
                    )
                    // ОТОБРАЖЕНИЕ БАЛЛОВ
                    Text(
                        text = "+${habit.points} pts",
                        style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.ExtraBold),
                        color = Color(0xFF4CAF50)
                    )
                }
                Text(
                    text = habit.description,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color(0xFF718096)
                )
                Spacer(modifier = Modifier.height(8.dp))
                LinearProgressIndicator(
                    progress = { animatedProgress },
                    modifier = Modifier.fillMaxWidth().height(6.dp),
                    color = if (animatedProgress >= 1f) Color(0xFF4CAF50) else Color(0xFF3B82F6),
                    trackColor = Color(0xFFD1D9E6),
                    strokeCap = androidx.compose.ui.graphics.StrokeCap.Round
                )
                Text(
                    text = "${habit.currentProgress}/${habit.targetCount}",
                    style = MaterialTheme.typography.labelSmall,
                    color = Color(0xFF718096),
                    modifier = Modifier.align(Alignment.End).padding(top = 4.dp)
                )
            }

            if (animatedProgress >= 1f) {
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = "Done",
                    tint = Color(0xFF4CAF50),
                    modifier = Modifier.size(32.dp).padding(start = 8.dp)
                )
            }
        }
    }
}

fun getIconForCategory(category: String): ImageVector {
    return when (category) {
        "Water" -> Icons.Rounded.WaterDrop
        "Transport" -> Icons.Rounded.DirectionsBike
        "Waste" -> Icons.Outlined.DeleteOutline
        "Energy" -> Icons.Rounded.Lightbulb
        else -> Icons.Rounded.Eco
    }
}