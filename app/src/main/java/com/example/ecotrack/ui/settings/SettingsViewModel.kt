package com.example.ecotrack.ui.settings

import android.app.Application
import androidx.lifecycle.AndroidViewModel

class SettingsViewModel(application: Application) : AndroidViewModel(application) {

    private val prefs =
        application.getSharedPreferences("eco_prefs", Application.MODE_PRIVATE)

    fun resetProgress() {
        prefs.edit()
            .putInt("habits_completed", 0)
            .apply()
    }
}
