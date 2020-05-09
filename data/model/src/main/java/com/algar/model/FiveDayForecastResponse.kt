package com.algar.model

data class FiveDayForecastResponse(
    val list: ArrayList<FiveDayDetails>,
    val city: City
)

fun FiveDayForecastResponse.mapToFiveDayForecastWithId() = FiveDayForecast(
    id = this.city.id,
    list = this.list
)