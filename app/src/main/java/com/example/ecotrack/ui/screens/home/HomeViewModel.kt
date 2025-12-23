package com.example.ecotrack.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecotrack.data.repository.HabitRepository
import com.example.ecotrack.domain.model.Habit
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val habitRepository: HabitRepository
) : ViewModel() {

    private val _habits = MutableStateFlow<List<Habit>>(emptyList())
    val habits: StateFlow<List<Habit>> = _habits

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    init {
        loadHabits()
    }

    private fun loadHabits() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null

            habitRepository.getHabitsFlow().collect { habits ->
                _habits.value = habits
                _isLoading.value = false
            }
        }
    }

    fun onHabitToggled(habit: Habit) {
        viewModelScope.launch {
            // Обновляем UI сразу
            val updatedList = _habits.value.map {
                if (it.id == habit.id) {
                    it.copy(isCompletedToday = !it.isCompletedToday)
                } else {
                    it
                }
            }
            _habits.value = updatedList

            // Отправляем на сервер
            val result = habitRepository.toggleHabitCompletion(
                habit.id,
                !habit.isCompletedToday
            )

            if (result.isFailure) {
                _error.value = "Не удалось обновить привычку"
                // Откатываем изменения при ошибке
                loadHabits()
            }
        }
    }

    fun retryLoading() {
        loadHabits()
    }
}