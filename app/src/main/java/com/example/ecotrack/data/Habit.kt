package com.example.ecotrack.data

/**
 * Модель данных для привычки.
 * В демо-версии id и title можно считать статичными,
 * а статус выполнения (isDone) будет меняться и сохраняться.
 */
data class Habit(
    val id: Int,      // Уникальный идентификатор (используется как ключ сохранения)
    val title: String, // Название привычки
    var isDone: Boolean // Статус выполнения (по умолчанию false)
)