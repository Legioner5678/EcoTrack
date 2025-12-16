package com.example.ecotrack.di

import android.content.Context
import androidx.room.Room
import com.example.ecotrack.data.database.AppDatabase // ВАЖНО: Мы пока создаем ссылку на будущий файл
import com.example.ecotrack.data.database.dao.EcoPointDao // Ссылка на DAO
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
        // Имя базы данных
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "eco_track_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideEcoPointDao(appDatabase: AppDatabase): EcoPointDao {
        // Этот метод будет доступен после того, как Дастан создаст DAO в п. 1.2
        return appDatabase.ecoPointDao()
    }
}