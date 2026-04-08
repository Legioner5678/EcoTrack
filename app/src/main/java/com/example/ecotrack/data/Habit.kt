package com.example.ecotrack.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "habits") // Помечаем как таблицу для Room
data class Habit(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,      // Сделаем 0 по умолчанию, чтобы Room сам генерировал ID
    val title: String,
    var isDone: Boolean = false
)
