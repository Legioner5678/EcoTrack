package com.example.ecotrack.data.repository

import android.util.Log
import com.example.ecotrack.data.database.dao.EcoPointDao
import com.example.ecotrack.data.database.dao.UserDao
import com.example.ecotrack.data.network.api.*
import com.example.ecotrack.domain.model.*
import kotlinx.coroutines.flow.Flow
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EcoTrackRepositoryImpl @Inject constructor(
    private val api: EcoBackendApiService,
    private val ecoPointDao: EcoPointDao,
    private val userDao: UserDao
) {
    val allHabits: Flow<List<Habit>> = ecoPointDao.getAllHabits()
    val totalPoints: Flow<Int?> = ecoPointDao.getTotalEcoPoints()

    // --- ФУНКЦИИ ДЛЯ СЕРВЕРА ---

    // Вход (твоя часть)
    suspend fun loginRemote(username: String, password: String): TokenResponse {
        return api.login(LoginRequest(username, password))
    }

    // Регистрация (часть сокомандника)
    suspend fun signUpRemote(username: String, email: String, password: String): SignUpResponse {
        return api.signUpUser(SignUpRequest(username, email, password))
    }

    // Синхронизация привычки с сервером
    suspend fun syncHabitWithServer(habitId: Int) = try {
        val response = api.completeHabit(habitId)
        userDao.updateUserStats(response.totalUserPoints, response.currentStreak)
        Result.success(response)
    } catch (e: Exception) {
        Result.failure(e)
    }

    // Получение точек карты
    suspend fun getRemoteEcoPoints(): List<EcoMapPoint> = try {
        api.getEcoPoints()
    } catch (e: Exception) {
        Log.e("REPO_ERROR", "Error fetching points: ${e.message}")
        emptyList()
    }

    // Покупка предмета через API
    suspend fun purchaseItem(itemId: Int, itemPrice: Int): Boolean {
        val balance = userDao.getCurrentBalance()
        if (balance < itemPrice) return false
        return try {
            val response = api.buyItem(itemId)
            if (response.isSuccess) {
                userDao.deductPoints(itemPrice)
                true
            } else false
        } catch (e: Exception) {
            Log.e("REPO_ERROR", "Purchase error: ${e.message}")
            false
        }
    }

    // --- ЛОКАЛЬНЫЕ ФУНКЦИИ (БАЗА ДАННЫХ) ---

    suspend fun updateHabit(habit: Habit) {
        ecoPointDao.updateHabit(habit)
    }

    suspend fun toggleHabit(habitId: Int, isDone: Boolean) {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        val startOfDay = calendar.timeInMillis
        calendar.set(Calendar.HOUR_OF_DAY, 23)
        val endOfDay = calendar.timeInMillis

        if (isDone) {
            ecoPointDao.insertLog(HabitLog(habitId = habitId, date = System.currentTimeMillis()))
        } else {
            ecoPointDao.deleteLogForDay(habitId, startOfDay, endOfDay)
        }
    }

    // Твой полный список привычек
    suspend fun seedHabits() {
        val defaultHabits = listOf(
            Habit(1, "Sorting", "Recycled plastic, glass or paper", 15, "Waste", 3, 0),
            Habit(2, "Eco-bag", "Used a reusable bag for shopping", 10, "General", 1, 0),
            Habit(3, "Eco Transport", "Walked, cycled or used public transport", 20, "Transport", 2, 0),
            Habit(4, "Water Bottle", "Used a reusable water bottle", 5, "Water", 5, 0),
            Habit(5, "Own Cup", "Coffee in your own reusable cup", 10, "Waste", 1, 0),
            Habit(6, "Lights Off", "Turned off unnecessary lights", 5, "Energy", 4, 0),
            Habit(7, "No Receipt", "Opted for a digital receipt", 5, "General", 1, 0),
            Habit(8, "Quick Shower", "Shower under 5 minutes", 10, "Water", 1, 0),
            Habit(9, "Plants Care", "Watered or planted something", 15, "General", 1, 0),
            Habit(10, "No Straw", "Declined a plastic straw", 5, "Waste", 1, 0),
            Habit(11, "Stairs", "Took stairs instead of elevator", 10, "Energy", 3, 0),
            Habit(12, "Repair", "Fixed something instead of buying new", 25, "Waste", 1, 0),
            Habit(13, "Local Food", "Bought from local farmers", 15, "Transport", 1, 0),
            Habit(14, "Eco-friendly", "Used safe household chemicals", 10, "Water", 1, 0)
        )
        defaultHabits.forEach { ecoPointDao.insertHabit(it) }
    }
}