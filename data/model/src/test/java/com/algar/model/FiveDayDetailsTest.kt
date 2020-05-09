package com.algar.model

import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class FiveDayDetailsTest {

    @Test
    fun `prettyDate() outputs fully qualified week name with time`() {
        val date = "2020-05-09 12:00:00"    // Saturday 12:00
        val fiveDayDetails = fakeFiveDayDetails().copy(date = date)

        val actual = fiveDayDetails.prettyDate()

        val expected = "Saturday 12:00"
        assertEquals(expected, actual)
    }

    // --------------------------- Helper methods ---------------------------

    private fun fakeFiveDayDetails(): FiveDayDetails {
        val fakeWeather = Weather(
            id = 1,
            main = "Foo",
            description = "Foo clear",
            icon = "02d"
        )
        val fakeFiveDayMain = FiveDayMain(
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
        return FiveDayDetails(
            dt = 1,
            date = "2020-05-09 12:00:00",
            weather = arrayListOf(fakeWeather),
            main = fakeFiveDayMain
        )
    }
}