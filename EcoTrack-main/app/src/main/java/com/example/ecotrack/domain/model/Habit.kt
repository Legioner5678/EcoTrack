package com.example.ecotrack.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "habits")
data class Habit(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val description: String,
    val points: Int = 10,           // Переименовали pointsReward -> points для совместимости
    val category: String = "General",
    val targetCount: Int = 1,
    val currentProgress: Int = 0,
    val lastCompletedDate: String? = null // ДОБАВИЛИ ЭТО: для фиксации даты (гггг-мм-дд)
)

@Entity(tableName = "habit_logs")
data class HabitLog(
    @PrimaryKey(autoGenerate = true)
    val logId: Long = 0,
    val habitId: Int,
    val date: Long,
    val isCompleted: Boolean = true
)