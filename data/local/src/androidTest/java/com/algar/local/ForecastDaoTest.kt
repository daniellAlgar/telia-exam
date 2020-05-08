package com.algar.local

import com.algar.common_test.ForecastDataSet.fakeWeatherGroup
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.Assert.assertEquals

class ForecastDaoTest : BaseTest() {

    @Test
    fun getAllCurrentForecastsInDatabase() = runBlocking {
        val numberOfCities = 3
        populateDatabaseWithForecasts(count = numberOfCities)

        forecastDao.getCurrentForecast().observeForever { forecasts ->
            assertEquals(numberOfCities, forecasts.size)
        }
    }

    // ---------------------------------- Helper methods ----------------------------------

    private suspend fun populateDatabaseWithForecasts(count: Int) {
        forecastDao.insert(currentForecast = fakeWeatherGroup(count = count))
    }
}