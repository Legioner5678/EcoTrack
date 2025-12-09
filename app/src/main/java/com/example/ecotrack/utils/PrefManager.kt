package com.example.ecotrack.utils

import android.content.Context

class PrefManager(context: Context) {


    private val prefs = context.getSharedPreferences("eco_prefs", Context.MODE_PRIVATE)

    fun saveUserName(name: String) {
        prefs.edit().putString("user_name", name).apply()
    }

    fun getUserName(): String {
        return prefs.getString("user_name", "Eco User") ?: "Eco User"
    }

    fun saveHabitsCount(count: Int) {
        prefs.edit().putInt("habits_count", count).apply()
    }

    fun getHabitsCount(): Int {
        return prefs.getInt("habits_count", 0)
    }

    fun resetProgress() {
        prefs.edit().putInt("habits_count", 0).apply()
    }
}
