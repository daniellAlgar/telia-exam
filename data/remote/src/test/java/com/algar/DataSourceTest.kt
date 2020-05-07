package com.algar

import com.algar.remote.model.ApiResponse.Success
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.Assert.assertEquals
import java.net.HttpURLConnection

class DataSourceTest : BaseTest() {

    @Test
    fun `Fetching group forecast successfully returns Success with data`() {
        mockHttpResponse(
            server = mockServer,
            fileName = "group_forecast.json",
            code = HttpURLConnection.HTTP_OK
        )

        runBlocking {
            val response = networkService.fetchGroupForecast()
            assert(response is Success)
            val forecasts = (response as Success).body
            val expectedNrOfForecasts = 3
            assertEquals(expectedNrOfForecasts, forecasts.list.size)
        }
    }
}