package data.local

import android.content.Context
import android.content.SharedPreferences

/**
 * Репозиторий для работы с локальным хранилищем (SharedPreferences).
 */
class HabitProgressRepository(context: Context) {

    // Имя файла SharedPreferences
    private val PREFS_NAME = "EcoTrackPrefs"
    private val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    // Ключи для данных
    private val KEY_USER_NAME = "user_name"
    // Префикс для статуса привычки: "habit_status_" + ID
    private val PREFIX_HABIT_STATUS = "habit_status_"

    // --- Функции для сохранения/загрузки имени пользователя ---

    /** Сохраняет имя пользователя. */
    fun saveUserName(name: String) {
        prefs.edit().putString(KEY_USER_NAME, name).apply()
    }

    /** Получает имя пользователя или значение по умолчанию. */
    fun getUserName(): String {
        return prefs.getString(KEY_USER_NAME, "Эко-Герой") ?: "Эко-Герой"
    }

    // --- Функции для сохранения/загрузки статуса привычек ---

    /** * Сохраняет статус выполнения привычки (true/false).
     * @param habitId ID привычки.
     * @param isDone Новый статус.
     */
    fun saveHabitStatus(habitId: Int, isDone: Boolean) {
        val key = PREFIX_HABIT_STATUS + habitId
        prefs.edit().putBoolean(key, isDone).apply()
    }

    /** * Получает сохраненный статус выполнения привычки.
     * @param habitId ID привычки.
     * @return true, если привычка была отмечена, иначе false.
     */
    fun getHabitStatus(habitId: Int): Boolean {
        val key = PREFIX_HABIT_STATUS + habitId
        // Если статус не найден (первый запуск), возвращаем false
        return prefs.getBoolean(key, false)
    }

    // --- Дополнительная функция для экрана Профиля ---

    /** Сбрасывает весь прогресс (удаляет все статусы привычек). */
    fun clearAllHabitProgress() {
        val editor = prefs.edit()

        // В упрощенной демо-версии можно просто удалить все ключи,
        // начинающиеся с нашего префикса.
        // Более правильный подход — удалять только нужные, но для демо сойдет:

        prefs.all.keys.forEach { key ->
            if (key.startsWith(PREFIX_HABIT_STATUS)) {
                editor.remove(key)
            }
        }
        editor.apply()
    }
}