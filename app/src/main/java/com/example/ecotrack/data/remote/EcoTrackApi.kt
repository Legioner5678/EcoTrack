package com.example.ecotrack.data.remote

import com.example.ecotrack.data.remote.dto.HabitCompletionRequest
import com.example.ecotrack.data.remote.dto.HabitCompletionResponse
import com.example.ecotrack.data.remote.dto.HabitsResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface EcoTrackApi {

    @GET("habits")
    suspend fun getHabits(
        @Query("category") category: String? = null
    ): Response<HabitsResponse>

    @GET("habits/recommended")
    suspend fun getRecommendedHabits(): Response<HabitsResponse>

    @POST("habits/complete")
    suspend fun completeHabit(
        @Body request: HabitCompletionRequest
    ): Response<HabitCompletionResponse>

    @GET("user/stats")
    suspend fun getUserStats(): Response<UserStatsDto>
}

// Добавь этот DTO для статистики
data class UserStatsDto(
    val totalHabitsCompleted: Int,
    val carbonSaved: Double,
    val currentStreak: Int
)