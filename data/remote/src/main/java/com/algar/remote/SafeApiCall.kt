package com.algar.remote

import com.algar.remote.model.ApiResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import kotlin.math.pow

/**
 * Wrapper method to make sure that the network call doesn't throw an exception that can crash the
 * application.
 */
suspend fun <T>safeApiCall(dispatcher: CoroutineDispatcher, apiCall: suspend () -> T): ApiResponse<T> {
    return withContext(dispatcher) {
        try {
            retryWithBackOff {
                ApiResponse.Success(apiCall.invoke())
            }
        } catch (error: Throwable) {
            parseError(exception = error)
        }
    }
}

private fun parseError(exception: Throwable): ApiResponse<Nothing> = when (exception) {
    is HttpException -> {
        val code = exception.code()
        val message = exception.response()?.errorBody()?.string()
        ApiResponse.Error(error = exception, code = code, message = message)
    }
    else -> ApiResponse.Error(error = exception, code = null, message = null)
}

/**
 * Adds an exponential backoff to network requests.
 */
private suspend fun <T> retryWithBackOff(
    times: Int = 5,
    initialDelay: Long = 100,   // milliseconds
    block: suspend () -> T): T
{
    var currentDelay = initialDelay
    repeat(times - 1) { tries ->
        try {
            return block()
        } catch (e: IOException) {
            // Can make a more fine grained analysis here to see if we want to continue the retry.
        }
        delay(currentDelay)
        currentDelay = (initialDelay * 2.0.pow(tries.toDouble())).toLong()
    }
    return block()  // last attempt
}