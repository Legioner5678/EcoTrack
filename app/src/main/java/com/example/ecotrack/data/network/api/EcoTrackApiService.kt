package com.example.ecotrack.data.network.api

import com.example.ecotrack.domain.model.EcoPointEntity
import retrofit2.http.*

/**
 * Модели данных для API качества воздуха (AQI)
 */
data class AirQualityResponse(
    val status: String,
    val data: AirData
)

data class AirData(
    val aqi: Int,
    val city: City
)

data class City(
    val name: String
)

/**
 * Главный интерфейс для работы с сетевыми запросами
 */
interface EcoTrackApiService {

    // 1. Получение данных о качестве воздуха (для главного экрана)
    @GET("feed/here/")
    suspend fun getAirQuality(
        @Query("token") token: String = "4d730b7f9d53e6b399cbea1a0f9044ec0363a703"
    ): AirQualityResponse

    // 2. Получение всех точек (привычек) с сервера
    @GET("ecopoints")
    suspend fun getRemoteEcoPoints(): List<EcoPointEntity>

    // 3. Отправка новой точки на сервер (то, что вызывало ошибку Unresolved reference)
    @POST("ecopoints")
    suspend fun uploadEcoPoint(@Body ecoPoint: EcoPointEntity): EcoPointEntity

    // 4. Удаление точки на сервере
    @DELETE("ecopoints/{id}")
    suspend fun deleteRemoteEcoPoint(@Path("id") id: Int)
}