package com.example.ecotrack.ui.profile.settings

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecotrack.data.database.dao.UserDao
import com.example.ecotrack.domain.model.UserEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val userDao: UserDao
) : ViewModel() {

    private val _currentUser = MutableStateFlow<UserEntity?>(null)
    val currentUser: StateFlow<UserEntity?> = _currentUser

    var currentLanguage = mutableStateOf("English")

    init {
        loadUserData()
    }

    private fun loadUserData() {
        viewModelScope.launch {
            userDao.getCurrentUser().collect { user ->
                _currentUser.value = user
            }
        }
    }

    fun updateNickname(newName: String) {
        viewModelScope.launch {
            _currentUser.value?.let { user ->
                val updatedUser = user.copy(name = newName)
                userDao.insertUser(updatedUser)
            }
        }
    }

    fun toggleLanguage() {
        currentLanguage.value = if (currentLanguage.value == "English") "Russian" else "English"
    }

    fun logout(onLogoutComplete: () -> Unit) {
        viewModelScope.launch {
            userDao.logoutAll()
            onLogoutComplete()
        }
    }
}