package com.example.ecotrack.data.network.api

import com.example.ecotrack.domain.model.EcoMapPoint
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

// Модели для ответов сервера (добавь их сюда же или в NewsModels)
data class HabitResponse(
    val totalUserPoints: Int,
    val currentStreak: Int
)

data class ShopResponse(
    val isSuccess: Boolean,
    val message: String
)

interface EcoBackendApiService {

    // Получение точек для карты
    @GET("api/eco-points/")
    suspend fun getEcoPoints(): List<EcoMapPoint>

    // Завершение привычки на сервере
    @POST("api/habits/{id}/complete/")
    suspend fun completeHabit(@Path("id") habitId: Int): HabitResponse

    // Покупка предмета
    @POST("api/shop/buy/{id}/")
    suspend fun buyItem(@Path("id") itemId: Int): ShopResponse
}