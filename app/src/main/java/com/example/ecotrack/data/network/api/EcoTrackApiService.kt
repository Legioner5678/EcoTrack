package com.example.ecotrack.data.network.api

import retrofit2.http.GET

/**
 * ЗАГЛУШКА: ЧАСТЬ РУСЛАНА
 * Руслан должен реализовать здесь все методы сетевого взаимодействия.
 */
interface EcoTrackApiService {

    // ЗАГЛУШКА: Временная функция для предотвращения ошибки компиляции
    @GET("/status/ping")
    suspend fun pingStatus(): String
}