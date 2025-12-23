package com.example.ecotrack.data.remote.dto

import com.google.gson.annotations.SerializedName

data class HabitDto(
    @SerializedName("id")
    val id: Int,

    @SerializedName("title")
    val title: String,

    @SerializedName("description")
    val description: String,

    @SerializedName("category")
    val category: String,

    @SerializedName("carbon_impact")
    val carbonImpact: Double
)

data class HabitsResponse(
    @SerializedName("habits")
    val habits: List<HabitDto>,

    @SerializedName("total")
    val total: Int
)

data class HabitCompletionRequest(
    @SerializedName("habit_id")
    val habitId: Int,

    @SerializedName("completed")
    val completed: Boolean,

    @SerializedName("date")
    val date: String // формат: "2024-12-23"
)

data class HabitCompletionResponse(
    @SerializedName("success")
    val success: Boolean,

    @SerializedName("message")
    val message: String
)