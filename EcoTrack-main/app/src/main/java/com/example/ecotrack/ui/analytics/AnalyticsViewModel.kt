package com.example.ecotrack.ui.analytics

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecotrack.data.repository.EcoTrackRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ImpactState(
    val waterSaved: Int = 0,    // литры
    val co2Reduced: Double = 0.0, // кг
    val points: Int = 0
)

@HiltViewModel
class AnalyticsViewModel @Inject constructor(
    private val repository: EcoTrackRepositoryImpl
) : ViewModel() {

    var impactState = mutableStateOf(ImpactState())
        private set

    init {
        viewModelScope.launch {
            repository.totalPoints.collectLatest { points ->
                val p = points ?: 0
                // Бизнес-логика: 1 балл = 2л воды, 0.1кг CO2
                impactState.value = ImpactState(
                    waterSaved = p * 2,
                    co2Reduced = p * 0.1,
                    points = p
                )
            }
        }
    }
}