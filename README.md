📘 EcoTrack — Intelligent System for Supporting an Environmentally Conscious Lifestyle

EcoTrack — это Android-приложение, созданное для поддержки экологически осознанного образа жизни.
Приложение помогает пользователям отслеживать ежедневные эко-привычки, улучшать экологическое поведение, анализировать собственный вклад и получать рекомендации по устойчивому образу жизни.

Разработано в рамках финального проекта по курсу Android Development и используется как основа для дипломной работы.

🌱 Основные возможности

Отслеживание экологических привычек (eco-habits)
Ведение прогресса и статистики привычек
Получение экологических советов и информационных материалов
Мотивация через систему баллов, уровней и streaks
Работа в оффлайн-режиме
Хранение пользовательских настроек
Чистая архитектура MVVM

🧩 Стек технологий
🟦 Язык программирования

Kotlin

📐 Архитектура

MVVM (Model–View–ViewModel)
Clean package structure
Repository pattern

🌐 Работа с сетью

Retrofit
Gson Converter
OkHttp Logging Interceptor

💾 Локальное хранение

Room Database
SharedPreferences

⚡ Асинхронность

Kotlin Coroutines
Flow / LiveData

🧭 Навигация

Jetpack Navigation Component

📁 Структура проекта
com.example.ecotrack/
│
├── data/
│   ├── api/           # Retrofit API interfaces
│   ├── model/         # Data models (DTO/Entities)
│   ├── repository/    # Repository pattern
│   └── local/         # Room database, DAOs
│
├── ui/
│   ├── home/          # Home screen
│   ├── habits/        # Eco habits screen
│   └── profile/       # Profile & settings
│
├── viewmodel/         # ViewModels for each screen
└── utils/             # Helpers, extensions, constants

🚀 План разработки
🔹 Функционал курса Android:

 Интеграция Retrofit
 MVVM-архитектура
 Room Database
 SharedPreferences
 Coroutines
 Работа через ветки и Pull Requests

🔹 Функционал для дипломной работы:

 Система геймификации eco-habits
 Аналитика и графики
 Модуль рекомендаций
 UI/UX оптимизация
 Расширенная статистика
 Система уровней/достижений

🛠 Запуск проекта
git clone https://github.com/Legioner5678/EcoTrack.git
cd EcoTrack
Открыть проект в Android Studio
Build → Make Project
Run → Run 'app'

👤 Автор

Ален Аугамбаев
Ruslan Tikhomirov
Daniyar Baksharov
Dastan Shukanov

🔒 Лицензирование

Проект находится в приватном репозитории и предназначен исключительно для учебных и исследовательских целей.
Публичное распространение и использование в сторонних проектах запрещено.

📌 Ссылка на репозиторий

🔒 Private:
https://github.com/Legioner5678/EcoTrack
