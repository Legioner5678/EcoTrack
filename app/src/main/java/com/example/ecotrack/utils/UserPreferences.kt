package com.example.ecotrack.utils

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore("user_prefs")

class UserPreferences(private val context: Context) {

    private val USERNAME_KEY = stringPreferencesKey("username")

    val userName: Flow<String> = context.dataStore.data
        .map { prefs -> prefs[USERNAME_KEY] ?: "Guest" }

    suspend fun saveUserName(name: String) {
        context.dataStore.edit { prefs ->
            prefs[USERNAME_KEY] = name
        }
    }
}
