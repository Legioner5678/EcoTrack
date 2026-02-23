package com.example.ecotrack.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecotrack.data.network.api.NewsApiService
import com.example.ecotrack.data.network.api.NewsArticle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

// HomeState остается в этом же файле
sealed class HomeState {
    object Loading : HomeState()
    data class Success(val articles: List<NewsArticle>) : HomeState()
    data class Error(val message: String) : HomeState()
}

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val newsApi: NewsApiService
) : ViewModel() {

    private val _uiState = MutableStateFlow<HomeState>(HomeState.Loading)
    val uiState: StateFlow<HomeState> = _uiState.asStateFlow()

    private val apiKey = "4368346543624509ad03314ad617cfb7"

    init {
        loadNews()
    }

    fun loadNews(customQuery: String? = null) {
        viewModelScope.launch {
            _uiState.value = HomeState.Loading
            try {
                // Если customQuery не передан, используем стандартный широкий поиск
                val query = customQuery ?: "(ecology OR climate OR sustainability)"

                val response = newsApi.getEcoNews(
                    query = query,
                    apiKey = apiKey
                )

                if (response.status == "ok") {
                    val filteredArticles = response.articles.filter { it.title != "[Removed]" }
                    _uiState.value = HomeState.Success(filteredArticles)
                } else {
                    _uiState.value = HomeState.Error("Сервер ответил: ${response.status}")
                }
            } catch (e: Exception) {
                _uiState.value = HomeState.Error(e.localizedMessage ?: "Неизвестная ошибка")
            }
        }
    }
}