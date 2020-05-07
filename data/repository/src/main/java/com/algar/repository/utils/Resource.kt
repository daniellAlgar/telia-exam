package com.algar.repository.utils

import com.algar.repository.utils.Resource.Status.ERROR
import com.algar.repository.utils.Resource.Status.LOADING
import com.algar.repository.utils.Resource.Status.SUCCESS

/**
 * Class that will allow e.g. a UI to observe the state of the data (loading, error or success) as
 * well as the actual [data].
 */
data class Resource<out T>(val status: Status, val data: T?, val error: Throwable?) {
    companion object {
        fun <T> success(data: T?): Resource<T> {
            return Resource(status = SUCCESS, data = data, error = null)
        }

        fun <T> error(error: Throwable, data: T?): Resource<T> {
            return Resource(status = ERROR, data = data, error = error)
        }

        fun <T> loading(data: T?): Resource<T> {
            return Resource(status = LOADING, data = data, error = null)
        }
    }

    enum class Status {
        SUCCESS,
        ERROR,
        LOADING
    }
}