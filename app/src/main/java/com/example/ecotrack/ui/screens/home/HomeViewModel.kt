package com.example.ecotrack.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecotrack.domain.model.Habit
// 1. ИМПОРТ РЕПОЗИТОРИЯ.
// !!! ВНИМАНИЕ: Проверь и при необходимости замени этот путь на актуальный в твоем проекте.
import com.example.ecotrack.data.repository.HabitRepository

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    // 2. ВНЕДРЕНИЕ ЗАВИСИМОСТИ (HabitRepository) через Hilt
    private val repository: HabitRepository
) : ViewModel() {

    // 3. ПОЛУЧЕНИЕ ДАННЫХ ИЗ ROOM
    // Преобразуем Flow<List<Habit>> из репозитория в StateFlow для Compose
    val habits: StateFlow<List<Habit>> = repository.getHabits() // Вызываем метод репозитория
        .stateIn(
            scope = viewModelScope,
            // Начинаем собирать данные, пока есть подписчики (Compose Screen)
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList() // Начальное значение
        )

    // 4. ЛОГИКА ОБНОВЛЕНИЯ ДАННЫХ
    fun onHabitToggled(habit: Habit) {
        viewModelScope.launch {
            // Создаем копию привычки с инвертированным статусом
            val updatedHabit = habit.copy(isCompletedToday = !habit.isCompletedToday)

            // Обновляем данные в базе данных через репозиторий
            repository.updateHabit(updatedHabit)
        }
    }
}