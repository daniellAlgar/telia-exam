package com.algar.remote

import com.algar.model.GroupForecastResponse
import com.algar.model.Secrets.apiKey
import retrofit2.http.GET

interface NetworkService {

    @GET("group?id=2711537,3117735,2643743&appid=$apiKey&units=metric")
    suspend fun getGroupForecast(): GroupForecastResponse
}