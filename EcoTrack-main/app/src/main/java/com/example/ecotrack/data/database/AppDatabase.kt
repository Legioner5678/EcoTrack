package com.example.ecotrack.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.ecotrack.data.database.dao.EcoPointDao
import com.example.ecotrack.data.database.dao.UserDao
import com.example.ecotrack.domain.model.EcoPointEntity
import com.example.ecotrack.domain.model.Habit
import com.example.ecotrack.domain.model.HabitLog
import com.example.ecotrack.domain.model.UserEntity

@Database(
    entities = [EcoPointEntity::class, Habit::class, HabitLog::class, UserEntity::class],
    version = 3, // Увеличили версию!
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun ecoPointDao(): EcoPointDao
    abstract fun userDao(): UserDao // Добавили DAO пользователя
}