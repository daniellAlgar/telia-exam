package com.algar

import com.algar.remote.model.ApiResponse.Success
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.Assert.assertEquals
import java.net.HttpURLConnection

class DataSourceTest : BaseTest() {

    @Test
    fun `Fetching city group forecast successfully returns Success with data`() {
        mockHttpResponse(
            server = mockServer,
            fileName = "group_forecast.json",
            code = HttpURLConnection.HTTP_OK
        )

        runBlocking {
            val response = networkService.fetchGroupForecast()
            assert(response is Success)
            val forecasts = (response as Success).body
            val expectedNumberOfForecasts = 3
            assertEquals(expectedNumberOfForecasts, forecasts.list.size)
        }
    }

    @Test
    fun `Fetching 5 day forecast successfully returns Success with data`() {
        val cityId = 2711537
        mockHttpResponse(
            server = mockServer,
            fileName = "5_day_forecast.json",
            code = HttpURLConnection.HTTP_OK
        )

        runBlocking {
            val response = networkService.fetchFiveDayForecast(cityId = cityId)
            assert(response is Success)
            val forecast = (response as Success).body
            val expectedNumberOfForecasts = 40
            assertEquals(expectedNumberOfForecasts, forecast.list.size)
        }
    }
}