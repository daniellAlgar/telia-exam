package com.algar.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.algar.model.GroupForecastResponse
import com.algar.repository.AppDispatchers
import com.algar.repository.WeatherRepository
import com.algar.repository.utils.Resource
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: WeatherRepository,
    private val dispatchers: AppDispatchers
) : ViewModel() {

    private val _forecasts = MutableLiveData<Resource<GroupForecastResponse>>()
    val forecasts: LiveData<Resource<GroupForecastResponse>> = _forecasts

    init {
        _forecasts.value = Resource.loading(data = null)
        getForecasts()
    }

    private fun getForecasts() = viewModelScope.launch(dispatchers.io) {
        _forecasts.value = repository.getGroupForecast()
    }
}