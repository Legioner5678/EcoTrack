package com.example.ecotrack.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.ecotrack.data.database.EcoPointEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface EcoPointDao {

    @Insert
    suspend fun insert(ecoPoint: EcoPointEntity)

    @Query("SELECT * FROM eco_points")
    fun getAllEcoPoints(): Flow<List<EcoPointEntity>>

    @Query("SELECT * FROM eco_points WHERE id = :id")
    suspend fun getEcoPointById(id: Int): EcoPointEntity?

    @Query("DELETE FROM eco_points")
    suspend fun deleteAll()
}