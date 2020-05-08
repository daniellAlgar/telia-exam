package com.algar.repository.utils

import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.algar.remote.model.ApiResponse
import com.algar.remote.model.ApiResponse.Error
import com.algar.remote.model.ApiResponse.Success
import com.algar.repository.utils.Resource.Companion.loading
import com.algar.repository.utils.Resource.Companion.success
import com.algar.repository.utils.Resource.Status.ERROR
import com.algar.repository.utils.Resource.Status.SUCCESS

/**
 * A generic class that can provide a resource backed by both the SQLite database and the network.
 * You can read more about it in the [Architecture Guide](https://developer.android.com/arch).
 *
 * Note 1: This class expects a network call to return an [ApiResponse].
 * Note 2: There is an optional function [onFetchFailed] that you can override.
 */
abstract class NetworkBoundResource<ResultType, RequestType>
@MainThread constructor(private val coroutines: CoroutineLaunch) {

    private val result = MediatorLiveData<Resource<ResultType>>()

    init {
        setValue(newValue = loading(data = null))
        @Suppress("LeakingThis")
        val dbSource = loadFromDb()
        result.addSourceOnce(dbSource) { data ->
            if (shouldFetch(data = data)) {
                fetchFromNetwork(dbSource = dbSource)
            } else {
                result.addSource(dbSource) { newData ->
                    setValue(newValue = success(newData))
                }
            }
        }
    }

    private fun fetchFromNetwork(dbSource: LiveData<ResultType>) = coroutines.io {
        // We re-attach dbSource as a new source, it will dispatch its latest value quickly
        coroutines.main {
            result.addSourceOnce(dbSource) { newData ->
                setValue(newValue = loading(newData))
            }
        }

        val (status, exception) = when (val apiResponse = createCall()) {
            is Success -> {
                saveCallResult(data = processResponse(response = apiResponse))
                SUCCESS to null
            }
            is Error -> {
                onFetchFailed()
                ERROR to apiResponse.error
            }
        }

        coroutines.main {
            result.addSource(loadFromDb()) { newData ->
                setValue(newValue = Resource(status = status, data = newData, error = exception))
            }
        }
    }

    @MainThread
    private fun setValue(newValue: Resource<ResultType>) = coroutines.main {
        if (result.value != newValue) {
            result.value = newValue
        }
    }

    fun asLiveData() = result as LiveData<Resource<ResultType>>

    /**
     * Optional method to override that determines what to do when a network call fails (i.e.
     * throws an error).
     */
    protected open fun onFetchFailed() {}

    @WorkerThread
    protected open fun processResponse(response: Success<RequestType>) = response.body

    @WorkerThread
    protected abstract suspend fun saveCallResult(data: RequestType)

    @MainThread
    protected abstract fun shouldFetch(data: ResultType?): Boolean

    @MainThread
    protected abstract fun loadFromDb(): LiveData<ResultType>

    @MainThread
    protected abstract suspend fun createCall(): ApiResponse<RequestType>
}