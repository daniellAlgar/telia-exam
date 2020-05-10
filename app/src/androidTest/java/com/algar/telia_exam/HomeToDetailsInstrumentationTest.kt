package com.algar.telia_exam

import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.algar.common_test.ForecastDataSet.fakeFiveDayForecast
import com.algar.common_test.ForecastDataSet.fakeWeatherGroup
import com.algar.common_test.matchers.RecyclerViewItemCountAssertion
import com.algar.model.CurrentForecast
import com.algar.model.FiveDayForecast
import com.algar.repository.utils.Resource
import io.mockk.coEvery
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HomeToDetailsInstrumentationTest : AppTestBase() {

    @Test
    fun homeToDetailsFlow() {
        val itemPosition = 2
        val numberOfTimeStamps = 8
        val stubWeatherGroup = fakeWeatherGroup(count = 3)
        val stubResource = Resource.success(data = stubWeatherGroup)
        val stubCityDetails = fakeFiveDayForecast(count = numberOfTimeStamps).copy(id = stubWeatherGroup[itemPosition].id)
        val stubCityDetailsResource = Resource.success(data = stubCityDetails)

        coEvery { repositoryMock.getGroupForecast() } returns MutableLiveData<Resource<List<CurrentForecast>>>().apply {
            postValue(stubResource)
        }
        coEvery { repositoryMock.getFiveDayForecast(cityId = stubWeatherGroup[itemPosition].id) } returns MutableLiveData<Resource<FiveDayForecast>>().apply {
            postValue(stubCityDetailsResource)
        }

        mainActivityTestRule.launchActivity(null)
        onView(withId(R.id.forecast_recycler_view)).perform(actionOnItemAtPosition<RecyclerView.ViewHolder>(itemPosition, ViewActions.click()))
        onView(withId(R.id.five_day_forecast_recycler_view)).check(
            RecyclerViewItemCountAssertion.withItemCount(expectedCount = numberOfTimeStamps)
        )
    }
}