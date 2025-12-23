package com.example.ecotrack.data.repository

import com.example.ecotrack.data.local.HabitProgressRepository
import com.example.ecotrack.data.remote.RetrofitClient
import com.example.ecotrack.data.remote.dto.HabitCompletionRequest
import com.example.ecotrack.domain.model.Habit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HabitRepository @Inject constructor(
    private val localRepository: HabitProgressRepository
) {

    // Получение привычек с API
    suspend fun getHabitsFromApi(): Result<List<Habit>> {
        return try {
            val response = RetrofitClient.api.getHabits()
            if (response.isSuccessful && response.body() != null) {
                val habits = response.body()!!.habits.map { dto ->
                    Habit(
                        id = dto.id,
                        title = dto.title,
                        description = dto.description,
                        isCompletedToday = false
                    )
                }
                Result.success(habits)
            } else {
                Result.failure(Exception("API Error: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // Flow для наблюдения за привычками
    fun getHabitsFlow(): Flow<List<Habit>> = flow {
        // Сначала пытаемся получить с API
        val result = getHabitsFromApi()
        if (result.isSuccess) {
            emit(result.getOrNull() ?: emptyList())
        } else {
            // Fallback на локальные данные
            emit(getLocalHabits())
        }
    }

    // Отметка привычки как выполненной
    suspend fun toggleHabitCompletion(habitId: Int, completed: Boolean): Result<Boolean> {
        return try {
            val request = HabitCompletionRequest(
                habitId = habitId,
                completed = completed,
                date = getCurrentDate()
            )
            val response = RetrofitClient.api.completeHabit(request)

            if (response.isSuccessful) {
                Result.success(true)
            } else {
                Result.failure(Exception("Failed to update habit"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private fun getLocalHabits(): List<Habit> {
        // Заглушка для локальных данных
        return listOf(
            Habit(1, "Сортировать отходы", "Пластик и бумага", false),
            Habit(2, "Меньше мяса", "Мясной вторник", false),
            Habit(3, "Многоразовая бутылка", "Использовать свою тару", false)
        )
    }

    private fun getCurrentDate(): String {
        val dateFormat = java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault())
        return dateFormat.format(java.util.Date())
    }
}