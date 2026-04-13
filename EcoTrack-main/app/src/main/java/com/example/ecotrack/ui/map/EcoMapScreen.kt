package com.example.ecotrack.ui.map

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*

@Composable
fun EcoMapScreen(viewModel: MapViewModel = hiltViewModel()) {
    val points by viewModel.points.collectAsState()

    // Начальная точка (например, Алматы или Астана)
    val defaultPos = LatLng(43.2389, 76.8897)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(defaultPos, 12f)
    }

    Scaffold { padding ->
        GoogleMap(
            modifier = Modifier.fillMaxSize().padding(padding),
            cameraPositionState = cameraPositionState,
            properties = MapProperties(isMyLocationEnabled = false) // Пока выключим для теста
        ) {
            // Рисуем маркеры только если они есть
            points.forEach { point ->
                Marker(
                    state = MarkerState(position = LatLng(point.latitude, point.longitude)),
                    title = point.name,
                    snippet = point.address
                )
            }
        }
    }
}