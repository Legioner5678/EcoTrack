package com.example.ecotrack.utils

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore by preferencesDataStore("user_prefs")

@Singleton
class UserPreferences @Inject constructor(private val context: Context) {

    companion object {
        private val USERNAME_KEY = stringPreferencesKey("username")
        private val CITY_KEY = stringPreferencesKey("city")
        private val AUTH_TOKEN_KEY = stringPreferencesKey("auth_token") // ДЛЯ JWT
    }

    // Сохранение токена (то, что просит AuthViewModel)
    suspend fun saveAuthToken(token: String) {
        context.dataStore.edit { prefs ->
            prefs[AUTH_TOKEN_KEY] = token
        }
    }

    val authToken: Flow<String?> = context.dataStore.data
        .map { prefs -> prefs[AUTH_TOKEN_KEY] }

    // Твои старые поля (оставляем для структуры)
    val userName: Flow<String> = context.dataStore.data
        .map { prefs -> prefs[USERNAME_KEY] ?: "Guest" }

    suspend fun saveUserName(name: String) {
        context.dataStore.edit { prefs ->
            prefs[USERNAME_KEY] = name
        }
    }

    suspend fun saveCity(city: String) {
        context.dataStore.edit { prefs ->
            prefs[CITY_KEY] = city
        }
    }
}