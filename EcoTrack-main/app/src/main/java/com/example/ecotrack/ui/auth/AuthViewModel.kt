package com.example.ecotrack.ui.auth

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecotrack.data.database.dao.UserDao
import com.example.ecotrack.data.repository.EcoTrackRepositoryImpl
import com.example.ecotrack.domain.model.UserEntity
import com.example.ecotrack.utils.UserPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val userDao: UserDao,
    private val repository: EcoTrackRepositoryImpl,
    private val userPreferences: UserPreferences
) : ViewModel() {

    var uiState = mutableStateOf<AuthState>(AuthState.Idle)
        private set

    fun login(username: String, passwordHash: String) {
        viewModelScope.launch {
            uiState.value = AuthState.Loading
            try {
                // Вызов сервера через репозиторий
                val tokenResponse = repository.loginRemote(username, passwordHash)

                // Сохраняем токен в настройки
                userPreferences.saveAuthToken(tokenResponse.access)

                // Обновляем локальную БД
                userDao.logoutAll()
                val existingUser = userDao.getUserByEmail(username)
                if (existingUser == null) {
                    userDao.insertUser(UserEntity(email = username, name = username, passwordHash = passwordHash, isLoggedIn = true))
                } else {
                    userDao.insertUser(existingUser.copy(isLoggedIn = true))
                }

                uiState.value = AuthState.Success
            } catch (e: Exception) {
                uiState.value = AuthState.Error("Server Login Failed: ${e.message}")
            }
        }
    }

    fun register(email: String, passwordHash: String) {
        viewModelScope.launch {
            uiState.value = AuthState.Loading
            try {
                // Регистрация на сервере Django
                repository.signUpRemote(email, email, passwordHash)
                uiState.value = AuthState.Success
            } catch (e: Exception) {
                uiState.value = AuthState.Error("Server Registration Failed: ${e.message}")
            }
        }
    }
}

sealed class AuthState {
    object Idle : AuthState()
    object Loading : AuthState()
    object Success : AuthState()
    data class Error(val message: String) : AuthState()
}