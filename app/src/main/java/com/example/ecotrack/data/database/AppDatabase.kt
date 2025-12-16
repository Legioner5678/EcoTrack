package com.example.ecotrack.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.ecotrack.data.database.dao.EcoPointDao
import com.example.ecotrack.domain.model.EcoPointEntity // Ссылка на будущую модель Дастана

/**
 * ЗАГЛУШКА: ЧАСТЬ ДАСТАНА
 * Дастан должен заменить [EcoPointEntity] на реальный класс сущности
 * и указать список всех сущностей в 'entities = [...]'.
 */
@Database(entities = [/* Здесь будут сущности Дастана */], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    // Дастан должен вернуть здесь реальный DAO
    abstract fun ecoPointDao(): EcoPointDao

    // ЗАГЛУШКА: Временный класс для предотвращения ошибки компиляции
    class EcoPointEntity // Временная заглушка
}