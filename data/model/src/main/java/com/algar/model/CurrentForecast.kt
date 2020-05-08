package com.algar.model

data class CurrentForecast(
    val id: Int,
    val dt: Long,
    val main: MainForecast,
    val weather: ArrayList<Weather>,
    val name: String
)