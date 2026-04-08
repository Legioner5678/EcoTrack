package com.example.ecotrack.data.repository

import com.example.ecotrack.data.database.dao.EcoPointDao
import com.example.ecotrack.data.network.api.EcoTrackApiService
import com.example.ecotrack.domain.model.EcoPointEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EcoTrackRepositoryImpl @Inject constructor(
    private val api: EcoTrackApiService,
    private val dao: EcoPointDao
) {

    val allEcoPoints: Flow<List<EcoPointEntity>> = dao.getAllHabits()

    suspend fun addEcoPoint(point: EcoPointEntity) {
        dao.insert(point)
    }

    suspend fun deleteEcoPoint(point: EcoPointEntity) {
        dao.deleteHabit(point)
    }
}