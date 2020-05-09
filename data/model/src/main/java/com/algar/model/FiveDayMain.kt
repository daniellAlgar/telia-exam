package com.algar.model

import com.google.gson.annotations.SerializedName

data class FiveDayMain(
    val temp: Float,
    @SerializedName("feels_like")
    val feelsLike: Float,
    @SerializedName("temp_min")
    val tempMin: Float,
    @SerializedName("temp_max")
    val tempMax: Float,
    val pressure: Int,
    @SerializedName("sea_level")
    val seaLevel: Int,
    @SerializedName("grnd_level")
    val groundLevel: Int,
    val humidity: Int,
    @SerializedName("temp_kf")
    val tempKf: Float
)
