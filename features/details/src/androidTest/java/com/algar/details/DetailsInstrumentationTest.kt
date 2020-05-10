package com.algar.details

import android.os.Bundle
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.algar.common_test.ForecastDataSet.fakeFiveDayForecast
import com.algar.common_test.matchers.RecyclerViewItemCountAssertion.Companion.withItemCount
import com.algar.details.RecyclerViewChildActions.Companion.childOfViewAtPositionWithMatcher
import com.algar.details.di.detailsModule
import com.algar.model.FiveDayForecast
import com.algar.repository.AppDispatchers
import com.algar.repository.WeatherRepository
import com.algar.repository.utils.Resource
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules
import org.koin.dsl.module
import org.koin.test.KoinTest

@RunWith(AndroidJUnit4::class)
class DetailsInstrumentationTest : KoinTest {

    private val repositoryMock = mockk<WeatherRepository>()
    private val argsMock = mockk<DetailsFragmentArgs>()

    private val modules = listOf(detailsModule, module {
        factory { repositoryMock }
        factory { AppDispatchers(
            main = Dispatchers.Main,
            io = Dispatchers.Unconfined
        ) }
    })

    @Before
    fun setup() {
        loadKoinModules(modules)
    }

    @After
    fun tearDown() {
        unloadKoinModules(modules)
    }

    /**
     * OBS: Requires to be run on api level >= 28!!!!!!!
     */
    @Test
    fun recyclerViewContainsAllItemsFedToIt() {
        val numberOfTimeStamps = 4
        val stubFiveDayForecast = fakeFiveDayForecast(count = numberOfTimeStamps)
        val stubResource = Resource.success(data = stubFiveDayForecast)
        val fragmentArgs = getBundle(forecast = stubFiveDayForecast)

        every { argsMock.cityId } returns stubFiveDayForecast.id
        every { argsMock.cityName } returns stubFiveDayForecast.name
        coEvery { repositoryMock.getFiveDayForecast(cityId = stubFiveDayForecast.id) } returns MutableLiveData<Resource<FiveDayForecast>>().apply {
            postValue(stubResource)
        }

        launchFragment(args = fragmentArgs)

        onView(withId(R.id.five_day_forecast_recycler_view)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(0))
        onView(withId(R.id.five_day_forecast_recycler_view)).check(withItemCount(expectedCount = numberOfTimeStamps))
        onView(withId(R.id.five_day_forecast_recycler_view))
            .check(matches(
                childOfViewAtPositionWithMatcher(
                    childId = R.id.weather,
                    position = 0,
                    childMatcher = withText(stubFiveDayForecast.list[0].weather[0].capitalizedDescription)
                )
            ))
    }

    /**
     * OBS: Requires to be run on api level >= 28!!!!!!!
     */
    @Test
    fun pullToRefreshFetchesNewData() {
        val stubFiveDayForecast = fakeFiveDayForecast(count = 3)
        val onCreateReturnValue = Resource.success(data = stubFiveDayForecast)
        val fragmentArgs = getBundle(forecast = stubFiveDayForecast)
        val cityId = stubFiveDayForecast.id

        every { argsMock.cityId } returns cityId
        every { argsMock.cityName } returns stubFiveDayForecast.name
        coEvery { repositoryMock.getFiveDayForecast(cityId = cityId) } returns MutableLiveData<Resource<FiveDayForecast>>().apply {
            postValue(onCreateReturnValue)
        }

        launchFragment(args = fragmentArgs)

        val refreshForecastsCount = 10
        val onRefreshReturnValue = Resource.success(data = fakeFiveDayForecast(count = refreshForecastsCount))
        coEvery { repositoryMock.getFiveDayForecast(cityId = cityId) } returns MutableLiveData<Resource<FiveDayForecast>>().apply {
            postValue(onRefreshReturnValue)
        }

        val recyclerId = R.id.five_day_forecast_recycler_view
        onView(withId(recyclerId)).perform(ViewActions.swipeDown())
        onView(withId(recyclerId)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(0))

        onView(withId(recyclerId)).check(withItemCount(expectedCount = refreshForecastsCount))
        onView(withId(R.id.swipe_to_refresh)).check { view, _ ->
            Assert.assertFalse(
                "This view shouldn't show a refresh progress at the moment!",
                (view as SwipeRefreshLayout).isRefreshing
            )
        }
    }

    /**
     * Helper methods
     */
    private fun launchFragment(args: Bundle? = null): NavController {
        val mockNavController = mockk<NavController>(relaxed = true)
        val detailsScenario = launchFragmentInContainer<DetailsFragment>(
            themeResId = R.style.AppTheme,
            fragmentArgs = args
        )

        detailsScenario.onFragment { fragment ->
            Navigation.setViewNavController(fragment.requireView(), mockNavController)
        }
        return mockNavController
    }

    private fun getBundle(forecast: FiveDayForecast) = Bundle().apply {
        putInt("cityId", forecast.id)
        putString("cityName", forecast.name)
    }
}