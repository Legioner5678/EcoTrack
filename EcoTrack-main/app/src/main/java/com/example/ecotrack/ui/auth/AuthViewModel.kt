package com.example.ecotrack.ui.auth

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecotrack.data.database.dao.UserDao
import com.example.ecotrack.domain.model.UserEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val userDao: UserDao
) : ViewModel() {

    var uiState = mutableStateOf<AuthState>(AuthState.Idle)
        private set

    fun register(email: String, passwordHash: String) {
        viewModelScope.launch {
            uiState.value = AuthState.Loading
            val existingUser = userDao.getUserByEmail(email)
            if (existingUser != null) {
                uiState.value = AuthState.Error("User with this email already exists")
            } else {
                val newUser = UserEntity(
                    email = email,
                    name = "Eco Hero", // Дефолтное имя, поменяют в настройках
                    passwordHash = passwordHash,
                    isLoggedIn = true
                )
                userDao.insertUser(newUser)
                uiState.value = AuthState.Success
            }
        }
    }

    fun login(email: String, passwordHash: String) {
        viewModelScope.launch {
            uiState.value = AuthState.Loading
            val user = userDao.getUserByEmail(email)
            if (user != null && user.passwordHash == passwordHash) {
                // Разлогиниваем всех остальных и логиним этого
                userDao.logoutAll()
                userDao.insertUser(user.copy(isLoggedIn = true))
                uiState.value = AuthState.Success
            } else {
                uiState.value = AuthState.Error("Invalid email or password")
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