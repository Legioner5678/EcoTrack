package com.example.ecotrack.ui.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecotrack.data.repository.EcoTrackRepositoryImpl
import com.example.ecotrack.domain.model.EcoMapPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val repository: EcoTrackRepositoryImpl
) : ViewModel() {

    private val _points = MutableStateFlow<List<EcoMapPoint>>(emptyList())
    val points: StateFlow<List<EcoMapPoint>> = _points

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    init {
        loadPoints()
    }

    fun loadPoints() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val result = repository.getRemoteEcoPoints()
                _points.value = result
            } catch (e: Exception) {
                _points.value = emptyList()
            } finally {
                _isLoading.value = false
            }
        }
    }
}