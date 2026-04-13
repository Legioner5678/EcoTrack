package com.example.ecotrack.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey val email: String,
    val name: String,
    val passwordHash: String,
    val city: String = "Almaty",
    val isLoggedIn: Boolean = false,
    val totalPoints: Int = 0,    // Текущий баланс для магазина
    val globalStreak: Int = 0    // Ударный режим (дней подряд)
)