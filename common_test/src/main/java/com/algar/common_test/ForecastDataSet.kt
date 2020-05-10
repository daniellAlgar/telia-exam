package com.algar.common_test

import com.algar.model.*

/**
 * Fake data for tests
 */
object ForecastDataSet {

    private val mainForecast = MainForecast(
        temp = 8.76f,
        tempMin = 7.78f,
        tempMax = 10f,
        pressure = 1016,
        humidity = 80,
        feelsLike = 4.87f
    )

    private val fakeWeather = Weather(
        id = 1,
        main = "Clear",
        description = "clear sky",
        icon = "01d"
    )

    private val fakeCurrentForecast = CurrentForecast(
        id = 1,
        dt = 2,
        name = "Foo",
        main = mainForecast,
        weather = arrayListOf(fakeWeather)
    )

    fun fakeWeatherGroup(count: Int = 1): ArrayList<CurrentForecast> {
        val output = arrayListOf<CurrentForecast>()
        (0 until count).mapTo(output) {
            fakeCurrentForecast.copy(
                id = it,
                name = fakeCurrentForecast.name + it
            )
        }

        return output
    }

    private val fakeFiveDayMain = FiveDayMain(
        temp = 1f,
        feelsLike = 1f,
        tempMin = 0f,
        tempMax = 2f,
        pressure = 10,
        seaLevel = 10,
        groundLevel = 10,
        humidity = 10,
        tempKf = 10f
    )

    private val fakeFiveDayDetails = FiveDayDetails(
        dt = 1,
        date = "2020-05-09 12:00:00",
        weather = arrayListOf(fakeWeather),
        main = fakeFiveDayMain
    )

    fun fakeFiveDayForecast(count: Int = 1): FiveDayForecast {
        val details = arrayListOf<FiveDayDetails>()
        (0 until count).mapTo(details) {
            fakeFiveDayDetails
        }

        return FiveDayForecast(
            id = 1,
            list = details,
            name = "Foo"
        )
    }
}