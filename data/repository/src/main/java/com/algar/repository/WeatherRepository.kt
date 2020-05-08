package com.algar.repository

import androidx.lifecycle.LiveData
import com.algar.local.dao.ForecastDao
import com.algar.model.CurrentForecast
import com.algar.model.GroupForecastResponse
import com.algar.remote.DataSource
import com.algar.remote.model.ApiResponse
import com.algar.repository.utils.CoroutineLaunch
import com.algar.repository.utils.NetworkBoundResource
import com.algar.repository.utils.Resource

interface WeatherRepository {
    suspend fun getGroupForecast(): LiveData<Resource<List<CurrentForecast>>>
}

class WeatherRepositoryImpl(
    private val service: DataSource,
    private val dao: ForecastDao,
    private val coroutine: CoroutineLaunch
) : WeatherRepository {

    override suspend fun getGroupForecast(): LiveData<Resource<List<CurrentForecast>>> {
        return object : NetworkBoundResource<List<CurrentForecast>, GroupForecastResponse>(coroutine) {

            override suspend fun saveCallResult(data: GroupForecastResponse) = dao.insert(currentForecast = data.list)

            override fun shouldFetch(data: List<CurrentForecast>?) = true

            override fun loadFromDb(): LiveData<List<CurrentForecast>> = dao.getCurrentForecast()

            override suspend fun createCall(): ApiResponse<GroupForecastResponse> = service.fetchGroupForecast()
        }.asLiveData()
    }
}