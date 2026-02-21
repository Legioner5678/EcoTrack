package com.example.ecotrack.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecotrack.data.network.api.NewsApiService
import com.example.ecotrack.data.network.api.NewsArticle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val newsApi: NewsApiService
) : ViewModel() {

    private val _news = MutableStateFlow<List<NewsArticle>>(emptyList())
    val news: StateFlow<List<NewsArticle>> = _news

    private val apiKey = "4368346543624509ad03314ad617cfb7"

    init {
        loadNews()
    }

    private fun loadNews() {
        viewModelScope.launch {
            try {
                val response = newsApi.getEcoNews(apiKey = apiKey)
                if (response.status == "ok") {
                    _news.value = response.articles
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}