package com.algar.model

data class FiveDayForecastResponse(
    val list: ArrayList<FiveDayDetails>,
    val city: City
)

fun FiveDayForecastResponse.mapToFiveDayForecastWithId() = FiveDayForecast(
    id = city.id,
    name = city.name,
    list = list
)