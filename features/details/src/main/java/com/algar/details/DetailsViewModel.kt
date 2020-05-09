package com.algar.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.algar.common.BaseViewModel
import com.algar.model.FiveDayForecast
import com.algar.repository.AppDispatchers
import com.algar.repository.WeatherRepository
import com.algar.repository.utils.Resource
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailsViewModel(
    private val repository: WeatherRepository,
    private val dispatchers: AppDispatchers
) : BaseViewModel() {

    private val _fiveDayForecast = MediatorLiveData<Resource<FiveDayForecast>>()
    val fiveDayForecast: LiveData<Resource<FiveDayForecast>> = _fiveDayForecast
    private var fiveDayForecastSource: LiveData<Resource<FiveDayForecast>> = MutableLiveData()

    fun fetchFiveDayForecast(id: Int) {
        getFiveDayForecast(id = id)
    }

    private fun getFiveDayForecast(id: Int) = viewModelScope.launch(dispatchers.main) {
        _fiveDayForecast.removeSource(fiveDayForecastSource)

        withContext(dispatchers.io) {
            fiveDayForecastSource = repository.getFiveDayForecast(cityId = id)
        }

        _fiveDayForecast.addSource(fiveDayForecastSource) {
            _fiveDayForecast.value = it
        }
    }

    fun pullToRefreshForecasts(cityId: Int) = fetchFiveDayForecast(id = cityId)
}