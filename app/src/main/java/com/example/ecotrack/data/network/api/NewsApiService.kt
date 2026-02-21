package com.example.ecotrack.data.network.api

import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {

    @GET("v2/everything")
    suspend fun getEcoNews(
        @Query("q") query: String = "environment OR climate OR sustainability",
        @Query("from") from: String = "2020-01-01",
        @Query("sortBy") sortBy: String = "relevancy",
        @Query("pageSize") pageSize: Int = 10,
        @Query("apiKey") apiKey: String
    ): NewsResponse
}