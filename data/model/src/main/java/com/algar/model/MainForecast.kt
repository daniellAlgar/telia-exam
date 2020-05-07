package com.algar.model

import com.google.gson.annotations.SerializedName

data class MainForecast(
    val temp: Float,
    @SerializedName("temp_min")
    val tempMin: Float,
    @SerializedName("temp_max")
    val tempMax: Float,
    val pressure: Int,
    val humidity: Int,
    @SerializedName("feels_like")
    val feelsLike: Float
)
