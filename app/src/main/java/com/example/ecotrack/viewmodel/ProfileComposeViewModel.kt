package com.example.ecotrack.viewmodel

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import com.example.ecotrack.utils.PrefManager

class ProfileComposeViewModel(application: Application) : AndroidViewModel(application) {

    private val prefs = PrefManager(application)

    var userName = mutableStateOf(prefs.getUserName())
        private set

    var habitsCount = mutableStateOf(prefs.getHabitsCount())
        private set

    fun saveName(name: String) {
        prefs.saveUserName(name)
        userName.value = name
    }

    fun resetProgress() {
        prefs.resetProgress()
        habitsCount.value = 0
    }

    // ✅ ТОЛЬКО ДЛЯ ТЕСТА
    fun addTestHabit() {
        val newValue = habitsCount.value + 1
        prefs.saveHabitsCount(newValue)
        habitsCount.value = newValue
    }
}
