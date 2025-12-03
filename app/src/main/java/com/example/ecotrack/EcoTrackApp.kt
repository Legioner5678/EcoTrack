package com.example.ecotrack

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

// Аннотация @HiltAndroidApp говорит Hilt, что это точка входа
@HiltAndroidApp
class EcoTrackApp : Application() {
    // Пока оставляем пустым
}