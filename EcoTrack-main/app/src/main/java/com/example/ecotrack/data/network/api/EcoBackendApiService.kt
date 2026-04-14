package com.example.ecotrack.data.network.api

import com.example.ecotrack.domain.model.EcoMapPoint
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Body

data class HabitResponse(val totalUserPoints: Int, val currentStreak: Int)
data class ShopResponse(val isSuccess: Boolean, val message: String)
data class LoginRequest(val username: String, val password: String)
data class TokenResponse(val access: String, val refresh: String)
data class SignUpRequest(val username: String, val email: String, val password: String)
data class SignUpResponse(val message: String?)

interface EcoBackendApiService {
    @POST("api/login/")
    suspend fun login(@Body request: LoginRequest): TokenResponse

    @POST("api/register/")
    suspend fun signUpUser(@Body request: SignUpRequest): SignUpResponse

    @GET("api/points/")
    suspend fun getEcoPoints(): List<EcoMapPoint>

    @POST("api/habits/{id}/complete/")
    suspend fun completeHabit(@Path("id") habitId: Int): HabitResponse

    @POST("api/shop/buy/{id}/")
    suspend fun buyItem(@Path("id") itemId: Int): ShopResponse
}