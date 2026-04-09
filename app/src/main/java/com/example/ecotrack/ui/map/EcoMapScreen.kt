package com.example.ecotrack.ui.map

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.example.ecotrack.domain.model.EcoMapPoint
import com.example.ecotrack.domain.model.EcoType
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*

@Composable
fun EcoMapScreen() {
    // Начальная точка — Алматы (КБТУ)
    val kbtu = LatLng(43.2551, 76.9415)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(kbtu, 12f)
    }

    // Тестовые точки приема вторсырья
    val ecoPoints = remember {
        listOf(
            EcoMapPoint("1", "Recycle Point #1", "Plastic & Paper", LatLng(43.2389, 76.8897), EcoType.PLASTIC),
            EcoMapPoint("2", "Glass Collection", "Only glass bottles", LatLng(43.2567, 76.9286), EcoType.GLASS),
            EcoMapPoint("3", "Battery Box", "Hazardous waste", LatLng(43.2610, 76.9450), EcoType.BATTERIES)
        )
    }

    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState,
        properties = MapProperties(isMyLocationEnabled = false) // Включим, когда настроим разрешения
    ) {
        ecoPoints.forEach { point ->
            Marker(
                state = MarkerState(position = point.position),
                title = point.title,
                snippet = point.snippet
            )
        }
    }
}