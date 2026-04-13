package com.example.ecotrack.viewmodel

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import com.google.ai.client.generativeai.type.generationConfig
import kotlinx.coroutines.launch

data class ChatMessage(val text: String, val isUser: Boolean)

class ChatViewModel : ViewModel() {
    val messages = mutableStateListOf<ChatMessage>()
    var isLoading by mutableStateOf(false)
        private set

    private val generativeModel = GenerativeModel(
        modelName = "gemini-1.5-flash",
        apiKey = "AIzaSyCG_Qdf-Y0fn0mRPuglqD95cTmzSch1x18", // В реальном проекте ключ лучше прятать в local.properties
        generationConfig = generationConfig { temperature = 0.7f },
        systemInstruction = content {
            text("You are the lead AI consultant for the EcoTrack system. " +
                    "Your mission is to support an eco-conscious lifestyle. " +
                    "You are guided by the concept of 'Micro-contributions for macro-results'. " +
                    "Your knowledge base includes: " +
                    "1. Waste sorting in Almaty, Kazakhstan (recycling points, rules). " +
                    "2. Ecological footprint calculation (e.g., 1 plastic bottle = 0.5kg CO2 during production). " +
                    "3. Facts: There are over 100 eco-boxes operating in Almaty. " +
                    "4. Motivation: Praise the user for completing habits in the tracker. " +
                    "Answer briefly (up to 3-4 sentences), using a scientific yet accessible language in English.")
        }
    )

    fun sendMessage(userMessage: String) {
        if (userMessage.isBlank()) return
        messages.add(ChatMessage(userMessage, true))
        isLoading = true

        viewModelScope.launch {
            try {
                val response = generativeModel.generateContent(userMessage)
                messages.add(ChatMessage(response.text ?: "Failed to get a response.", false))
            } catch (e: Exception) {
                messages.add(ChatMessage("Connection error. Please check your internet.", false))
            } finally {
                isLoading = false
            }
        }
    }
}