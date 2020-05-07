package com.algar.repository

import com.algar.model.GroupForecastResponse
import com.algar.remote.DataSource
import com.algar.remote.model.ApiResponse
import com.algar.repository.utils.Resource
import com.algar.repository.utils.Resource.Status.ERROR
import com.algar.repository.utils.Resource.Status.SUCCESS

interface WeatherRepository {
    suspend fun getGroupForecast(): Resource<GroupForecastResponse>
}

class WeatherRepositoryImpl(private val service: DataSource) : WeatherRepository {

    override suspend fun getGroupForecast(): Resource<GroupForecastResponse> {
        return when (val apiResponse = service.fetchGroupForecast()) {
            is ApiResponse.Success -> {
                Resource(status = SUCCESS, data = apiResponse.body, error = null)
            }
            is ApiResponse.Error -> {
                Resource(status = ERROR, data = null, error = apiResponse.error)
            }
        }
    }
}