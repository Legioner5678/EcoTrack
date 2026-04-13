package com.example.ecotrack.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecotrack.data.database.dao.UserDao // Добавили
import com.example.ecotrack.data.repository.EcoTrackRepositoryImpl
import com.example.ecotrack.domain.model.Habit
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileComposeViewModel @Inject constructor(
    private val repository: EcoTrackRepositoryImpl,
    private val userDao: UserDao // Добавили инъекцию UserDao
) : ViewModel() {

    // Подписка на данные пользователя (имя, email и т.д.)
    val currentUser = userDao.getCurrentUser()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    // Поток всех привычек
    val allHabits: StateFlow<List<Habit>> = repository.allHabits
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Поток общего количества очков
    val totalPoints: StateFlow<Int> = repository.totalPoints
        .map { it ?: 0 }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    fun resetProgress() {
        viewModelScope.launch {
            // Реализация сброса, если потребуется
        }
    }
}