package com.algar.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.algar.common_test.ForecastDataSet
import com.algar.model.CurrentForecast
import com.algar.repository.AppDispatchers
import com.algar.repository.WeatherRepository
import com.algar.repository.utils.Resource
import io.mockk.coEvery
import io.mockk.confirmVerified
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class HomeUnitTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var repository: WeatherRepository
    private val testDispatchers = AppDispatchers(
        main = Dispatchers.Unconfined,
        io = Dispatchers.Unconfined
    )

    @Before
    fun setup() {
        repository = mockk()
    }

    @Test
    fun `Forecasts are automatically requested when ViewModel is created`() {
        val observer = mockk<Observer<Resource<*>>>(relaxed = true)
        val stubResult = Resource.success(data = ForecastDataSet.fakeWeatherGroup(count = 10))
        coEvery { repository.getGroupForecast() } returns MutableLiveData<Resource<List<CurrentForecast>>>().apply {
            value = stubResult
        }

        val homeViewModel = HomeViewModel(
            repository = repository,
            dispatchers = testDispatchers
        )
        homeViewModel.forecast.observeForever(observer)
        verify {
            observer.onChanged(stubResult)
        }

        confirmVerified(observer)
    }
}