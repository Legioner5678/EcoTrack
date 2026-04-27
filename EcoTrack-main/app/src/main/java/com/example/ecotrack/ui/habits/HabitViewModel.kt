package com.example.ecotrack.ui.habits

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecotrack.data.repository.EcoTrackRepositoryImpl
import com.example.ecotrack.domain.model.Habit
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HabitViewModel @Inject constructor(
    private val repository: EcoTrackRepositoryImpl
) : ViewModel() {

    val habits: StateFlow<List<Habit>> = repository.allHabits
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    fun incrementHabit(habit: Habit) {
        if (habit.currentProgress < habit.targetCount) {
            val updatedHabit = habit.copy(currentProgress = habit.currentProgress + 1)

            viewModelScope.launch {
                repository.updateHabit(updatedHabit)
                if (updatedHabit.currentProgress == updatedHabit.targetCount) {
                    val finalHabit = updatedHabit.copy(
                        lastCompletedDate = System.currentTimeMillis().toString()
                    )
                    repository.updateHabit(finalHabit)
                    repository.toggleHabit(habit.id, true)
                    repository.syncHabitWithServer(habit.id)
                }
            }
        }
    }

    init {
        viewModelScope.launch {
            repository.seedHabits()
        }
    }
}