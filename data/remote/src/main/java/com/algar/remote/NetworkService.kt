package com.algar.remote

import com.algar.model.FiveDayForecastResponse
import com.algar.model.GroupForecastResponse
import com.algar.model.Secrets.apiKey
import retrofit2.http.GET
import retrofit2.http.Query

interface NetworkService {

    @GET("group?id=2711537,3117735,2643743&appid=$apiKey&units=metric")
    suspend fun getGroupForecast(): GroupForecastResponse

    @GET("forecast?appid=$apiKey&units=metric")
    suspend fun getFiveDayForecast(@Query("id") cityId: Int): FiveDayForecastResponse
}