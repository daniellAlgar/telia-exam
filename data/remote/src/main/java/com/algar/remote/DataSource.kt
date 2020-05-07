package com.algar.remote

import com.algar.model.GroupForecastResponse
import com.algar.remote.model.ApiResponse
import kotlinx.coroutines.CoroutineDispatcher

/**
 * Implementation of [NetworkService] interface.
 */
class DataSource(
    private val service: NetworkService,
    private val dispatcher: CoroutineDispatcher
) {

    suspend fun fetchGroupForecast(): ApiResponse<GroupForecastResponse> = safeApiCall(dispatcher = dispatcher) {
        service.getGroupForecast()
    }
}