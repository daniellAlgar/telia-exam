package com.algar.details

import android.os.Bundle
import android.widget.TextView
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.algar.common_test.ForecastDataSet
import com.algar.common_test.ForecastDataSet.fakeFiveDayForecast
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
    fun checkThatTheWeatherDescriptionIsShown() {
        val stubFiveDayForecast = fakeFiveDayForecast(count = 8)
        val stubResource = Resource.success(data = stubFiveDayForecast)
        val fragmentArgs = Bundle().apply {
            putInt("cityId", stubFiveDayForecast.id)
            putString("cityName", stubFiveDayForecast.name)
        }
        every { argsMock.cityId } returns stubFiveDayForecast.id
        every { argsMock.cityName } returns stubFiveDayForecast.name
        coEvery { repositoryMock.getFiveDayForecast(cityId = stubFiveDayForecast.id) } returns MutableLiveData<Resource<FiveDayForecast>>().apply {
            postValue(stubResource)
        }

        launchFragment(args = fragmentArgs)

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
}