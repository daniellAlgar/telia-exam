package com.algar.home

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.swipeDown
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.algar.common_test.ForecastDataSet.fakeWeatherGroup
import com.algar.home.di.homeModule
import com.algar.common_test.matchers.RecyclerViewItemCountAssertion.Companion.withItemCount
import com.algar.model.CurrentForecast
import com.algar.repository.AppDispatchers
import com.algar.repository.WeatherRepository
import com.algar.repository.utils.Resource
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.Assert.assertFalse
import org.junit.runner.RunWith
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules
import org.koin.dsl.module
import org.koin.test.KoinTest

@RunWith(AndroidJUnit4::class)
class HomeInstrumentationTest : KoinTest {

    private val repositoryMock = mockk<WeatherRepository>()

    private val modules = listOf(homeModule, module {
        factory { AppDispatchers(main = Dispatchers.Unconfined, io = Dispatchers.Unconfined) }
        factory { repositoryMock }
    })

    @Before
    fun setup() {
        loadKoinModules(modules)
    }

    @After
    fun tearDown() {
        unloadKoinModules(modules)
    }

    @Test
    fun recyclerViewContainsAllItemsFedToIt() {
        val numberOfCities = 10
        val stubResult = Resource.success(data = fakeWeatherGroup(count = numberOfCities))
        coEvery { repositoryMock.getGroupForecast() } returns MutableLiveData<Resource<List<CurrentForecast>>>().apply {
            postValue(stubResult)
        }

        launchFragment()
        onView(withId(R.id.forecast_recycler_view)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(0))

        onView(withId(R.id.forecast_recycler_view)).check(withItemCount(expectedCount = numberOfCities))
    }

    @Test
    fun pullToRefreshFetchesNewData() {
        val recyclerId = R.id.forecast_recycler_view
        val onCreateReturnValue = Resource.success(data = fakeWeatherGroup(count = 3))
        coEvery { repositoryMock.getGroupForecast() } returns MutableLiveData<Resource<List<CurrentForecast>>>().apply {
            postValue(onCreateReturnValue)
        }

        launchFragment()
        val refreshForecastsCount = 10
        val onRefreshReturnValue = Resource.success(data = fakeWeatherGroup(count = refreshForecastsCount))
        coEvery { repositoryMock.getGroupForecast() } returns MutableLiveData<Resource<List<CurrentForecast>>>().apply {
            postValue(onRefreshReturnValue)
        }
        onView(withId(recyclerId)).perform(swipeDown())
        onView(withId(recyclerId)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(0))

        onView(withId(recyclerId)).check(withItemCount(expectedCount = refreshForecastsCount))
        onView(withId(R.id.swipe_refresh_group_forecast)).check { view, _ ->
            assertFalse("This view shouldn't show a refresh progress at the moment!", (view as SwipeRefreshLayout).isRefreshing)
        }
    }

    /**
     * Helper methods
     */
    private fun launchFragment(): NavController {
        val mockNavController = mockk<NavController>(relaxed = true)
        val homeScenario = launchFragmentInContainer<HomeFragment>(themeResId = R.style.AppTheme)

        homeScenario.onFragment { fragment ->
            Navigation.setViewNavController(fragment.requireView(), mockNavController)
        }
        return mockNavController
    }
}