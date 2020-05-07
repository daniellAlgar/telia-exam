package com.algar.model

data class CurrentForecast(
    val main: MainForecast,
    val weather: ArrayList<Weather>,
    val name: String
)