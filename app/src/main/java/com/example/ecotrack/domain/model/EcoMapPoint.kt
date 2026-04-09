package com.example.ecotrack.domain.model

import com.google.android.gms.maps.model.LatLng

data class EcoMapPoint(
    val id: String,
    val title: String,
    val snippet: String, // Описание (например, "Accepts plastic and glass")
    val position: LatLng,
    val type: EcoType
)

enum class EcoType {
    PLASTIC, GLASS, BATTERIES, PAPER
}