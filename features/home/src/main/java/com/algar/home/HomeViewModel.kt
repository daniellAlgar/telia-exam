package com.algar.home

import androidx.lifecycle.*
import com.algar.common.BaseViewModel
import com.algar.model.CurrentForecast
import com.algar.repository.AppDispatchers
import com.algar.repository.WeatherRepository
import com.algar.repository.utils.Resource
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel(
    private val repository: WeatherRepository,
    private val dispatchers: AppDispatchers
) : BaseViewModel() {

    private val _forecast = MediatorLiveData<Resource<List<CurrentForecast>>>()
    val forecast: LiveData<Resource<List<CurrentForecast>>> = _forecast
    private var forecastSource: LiveData<Resource<List<CurrentForecast>>> = MutableLiveData()

    init {
        _forecast.value = Resource.loading(data = null)
        getCurrentForecast()
    }

    private fun getCurrentForecast() = viewModelScope.launch(dispatchers.main) {
        _forecast.removeSource(forecastSource)

        withContext(dispatchers.io) {
            forecastSource = repository.getGroupForecast()
        }

        _forecast.addSource(forecastSource) {
            _forecast.value = it
        }
    }
}