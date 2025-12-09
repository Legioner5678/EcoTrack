package com.example.ecotrack.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecotrack.domain.model.Habit
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    // Здесь пока пусто. Позже сюда придет UseCase/Repository от Руслана.
) : ViewModel() {

    // Состояние, которое будет слушать Compose
    private val _habits = MutableStateFlow<List<Habit>>(emptyList())
    val habits: StateFlow<List<Habit>> = _habits

    init {
        loadStaticHabits()
    }

    private fun loadStaticHabits() {
        // Статические данные для быстрой верстки (заглушка)
        viewModelScope.launch {
            _habits.value = listOf(
                Habit(1, "Сортировать отходы", "Пластик и бумага", isCompletedToday = true),
                Habit(2, "Меньше мяса", "Мясной вторник", isCompletedToday = false),
                Habit(3, "Многоразовая бутылка", "Использовать свою тару", isCompletedToday = false),
            )
        }
    }

    // Логика нажатия на "Выполнено"
    fun onHabitToggled(habit: Habit) {
        // Логика обновления в списке
        viewModelScope.launch {
            val updatedList = _habits.value.map {
                if (it.id == habit.id) {
                    it.copy(isCompletedToday = !it.isCompletedToday)
                } else {
                    it
                }
            }
            _habits.value = updatedList
        }
    }
}
