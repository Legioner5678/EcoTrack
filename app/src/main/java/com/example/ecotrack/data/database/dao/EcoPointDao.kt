package com.example.ecotrack.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.ecotrack.data.database.AppDatabase
import kotlinx.coroutines.flow.Flow

/**
 * ЗАГЛУШКА: ЧАСТЬ ДАСТАНА
 * Дастан должен реализовать здесь все методы доступа к данным (DAO).
 */
@Dao
interface EcoPointDao {

    // ЗАГЛУШКА: Временная функция для предотвращения ошибки компиляции
    @Query("SELECT 'OK' AS TEMP_STATUS")
    fun getEcoPoints(): Flow<List<AppDatabase.EcoPointEntity>>
}