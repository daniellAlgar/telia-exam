package com.algar.common_test

import com.algar.model.CurrentForecast
import com.algar.model.MainForecast
import com.algar.model.Weather

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
}