package com.example.ecotrack.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecotrack.data.network.api.AirData
import com.example.ecotrack.data.network.api.EcoTrackApiService
import com.example.ecotrack.domain.model.Habit
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val apiService: EcoTrackApiService // Внедряем API
) : ViewModel() {

    private val _habits = MutableStateFlow<List<Habit>>(emptyList())
    val habits: StateFlow<List<Habit>> = _habits

    // Добавляем состояние для AQI
    private val _aqiData = MutableStateFlow<AirData?>(null)
    val aqiData: StateFlow<AirData?> = _aqiData

    init {
        loadStaticHabits()
        fetchAirQuality() // Запускаем загрузку данных из API
    }

    private fun loadStaticHabits() {
        TODO("Not yet implemented")
    }

    private fun fetchAirQuality() {
        viewModelScope.launch {
            try {
                val response = apiService.getAirQuality()
                if (response.status == "ok") {
                    _aqiData.value = response.data
                }
            } catch (e: Exception) {
                // Обработка ошибки
            }
        }
    }
    // ... ваш метод loadStaticHabits и onHabitToggled остаются без изменений

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
