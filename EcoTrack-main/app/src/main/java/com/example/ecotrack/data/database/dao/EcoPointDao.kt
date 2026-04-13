package com.example.ecotrack.data.database.dao

import androidx.room.*
import com.example.ecotrack.domain.model.EcoPointEntity
import com.example.ecotrack.domain.model.Habit
import com.example.ecotrack.domain.model.HabitLog
import kotlinx.coroutines.flow.Flow

@Dao
interface EcoPointDao {
    // --- ТОЧКИ НА КАРТЕ ---
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(point: EcoPointEntity)

    @Query("SELECT * FROM eco_points ORDER BY date DESC")
    fun getAllPoints(): Flow<List<EcoPointEntity>>

    @Delete
    suspend fun deletePoint(point: EcoPointEntity)

    // --- ПРИВЫЧКИ ---
    @Query("SELECT * FROM habits")
    fun getAllHabits(): Flow<List<Habit>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHabit(habit: Habit)

    // НОВОЕ: Обновление прогресса привычки
    @Update
    suspend fun updateHabit(habit: Habit)

    @Insert
    suspend fun insertLog(log: HabitLog)

    @Query("SELECT * FROM habit_logs WHERE habitId = :habitId AND date >= :start AND date <= :end")
    suspend fun getLogForDay(habitId: Int, start: Long, end: Long): HabitLog?

    @Query("DELETE FROM habit_logs WHERE habitId = :habitId AND date >= :start AND date <= :end")
    suspend fun deleteLogForDay(habitId: Int, start: Long, end: Long)

    @Query("SELECT SUM(h.points) FROM habits h INNER JOIN habit_logs l ON h.id = l.habitId")
    fun getTotalEcoPoints(): Flow<Int?>
}