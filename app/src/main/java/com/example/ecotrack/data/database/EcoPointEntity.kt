package com.example.ecotrack.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "eco_points")
data class EcoPointEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val name: String,
    val latitude: Double,
    val longitude: Double,
    val type: String, // например: "recycle", "water", "energy"
    val description: String,
    val savedCarbon: Double = 0.0
)