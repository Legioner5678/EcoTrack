package com.example.ecotrack.data.repository

import com.example.ecotrack.data.database.dao.EcoPointDao
import com.example.ecotrack.data.network.api.EcoTrackApiService
import com.example.ecotrack.domain.model.EcoPointEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EcoTrackRepository @Inject constructor(
    private val api: EcoTrackApiService,
    private val dao: EcoPointDao
) {

    // Получаем поток данных из БД (Данияр подключит это к UI)
    val allEcoPoints: Flow<List<EcoPointEntity>> = dao.getAllHabits()

    // Получаем общую сумму очков
    val totalPoints: Flow<Int?> = dao.getTotalPoints()

    /**
     * Логика добавления новой точки (привычки)
     * Сначала сохраняем в локальную БД, затем отправляем на сервер
     */
    suspend fun addEcoPoint(ecoPoint: EcoPointEntity) {
        // 1. Сохраняем локально (Работает сразу даже без интернета)
        dao.insert(ecoPoint)

        // 2. Синхронизируем с сервером
        try {
            api.uploadEcoPoint(ecoPoint)
        } catch (e: Exception) {
            // Ошибка сети — данные уже в БД, можно будет доотправить позже
            e.printStackTrace()
        }
    }

    /**
     * Синхронизация данных с сервера в локальную БД
     */
    suspend fun syncWithServer() {
        try {
            val remotePoints = api.getRemoteEcoPoints()
            // Очищаем старые и записываем новые данные
            dao.clearAllHabits()
            remotePoints.forEach { dao.insert(it) }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * Удаление точки
     */
    suspend fun deletePoint(ecoPoint: EcoPointEntity) {
        dao.deleteHabit(ecoPoint)
        try {
            // Предположим, у сущности есть поле id для удаления на сервере
            // api.deleteRemoteEcoPoint(ecoPoint.id)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}