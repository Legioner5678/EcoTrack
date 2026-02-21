package com.example.ecotrack.di

import com.example.ecotrack.data.network.api.EcoTrackApiService
import com.example.ecotrack.data.network.api.NewsApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttp(): OkHttpClient =
        OkHttpClient.Builder().build()

    // 🔵 Retrofit для НОВОСТЕЙ
    @Provides
    @Singleton
    @NewsRetrofit
    fun provideNewsRetrofit(
        client: OkHttpClient
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl("https://newsapi.org/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    // 🟢 Retrofit для AQI (если нужен)
    @Provides
    @Singleton
    @AqiRetrofit
    fun provideAqiRetrofit(
        client: OkHttpClient
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl("https://api.waqi.info/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    // 📰 News API
    @Provides
    @Singleton
    fun provideNewsApiService(
        @NewsRetrofit retrofit: Retrofit
    ): NewsApiService =
        retrofit.create(NewsApiService::class.java)
}