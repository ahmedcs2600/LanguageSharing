package com.app.remote.core

import retrofit2.Response
import java.lang.Exception

sealed class ApiResponse<out T> {

    data class ApiSuccessResponse<T>(val response: Response<T>) : ApiResponse<T>() {
        val data: T? = response.body()
    }

    sealed class ApiFailureResponse<T> {
        data class Error<T>(val response: Response<T>) : ApiResponse<T>()

        data class Exception<T>(val exception: Throwable) : ApiResponse<T>() {
            val message: String? = exception.localizedMessage
        }
    }

    companion object {
        fun <T> create(
            successCodeRange: IntRange = 200..299,
            response: Response<T>
        ): ApiResponse<T> {
            return try {
                if (response.code() in successCodeRange) {
                    ApiSuccessResponse(response)
                } else {
                    ApiFailureResponse.Error(response)
                }
            } catch (ex: Exception) {
                ApiFailureResponse.Exception(ex)
            }
        }
    }
}