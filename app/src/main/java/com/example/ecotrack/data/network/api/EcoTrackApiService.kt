package com.example.ecotrack.data.network.api

import retrofit2.http.GET
import com.example.ecotrack.data.database.dao.EcoPointDao // Предполагаем, что DTO создал Дастан
import com.example.ecotrack.domain.model.EcoPointEntity // Используем твою сущность или создай DTO
import retrofit2.http.*

interface EcoTrackApiService {

    @GET("ecopoints")
    suspend fun getRemoteEcoPoints(): List<EcoPointEntity>

    @POST("ecopoints")
    suspend fun uploadEcoPoint(@Body ecoPoint: EcoPointEntity): EcoPointEntity

    @DELETE("ecopoints/{id}")
    suspend fun deleteRemoteEcoPoint(@Path("id") id: Int)
}
