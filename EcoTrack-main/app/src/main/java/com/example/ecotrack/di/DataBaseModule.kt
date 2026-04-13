package com.example.ecotrack.di

import android.content.Context
import androidx.room.Room
import com.example.ecotrack.data.database.AppDatabase
import com.example.ecotrack.data.database.dao.EcoPointDao
import com.example.ecotrack.data.database.dao.UserDao // ДОБАВИЛИ ЭТОТ ИМПОРТ
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "eco_track_database" // Сохраняем твое оригинальное имя базы
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideEcoPointDao(db: AppDatabase): EcoPointDao {
        return db.ecoPointDao()
    }

    // ДОБАВИЛИ ЭТОТ МЕТОД, чтобы Hilt знал, как создать UserDao
    @Provides
    fun provideUserDao(db: AppDatabase): UserDao {
        return db.userDao()
    }
}