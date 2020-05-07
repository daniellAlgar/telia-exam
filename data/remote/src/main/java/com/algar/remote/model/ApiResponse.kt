package com.algar.remote.model

sealed class ApiResponse<out T> {
    data class Success<out T>(val body: T): ApiResponse<T>()
    data class Error(
        val error: Throwable,
        val code: Int? = null,
        val message: String? = null
    ): ApiResponse<Nothing>()
}