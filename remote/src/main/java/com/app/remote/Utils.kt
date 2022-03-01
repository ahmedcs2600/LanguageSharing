package com.app.remote

import com.app.remote.core.ApiResponse

fun <T> ApiResponse.ApiFailureResponse.Error<T>.message(): String = toString()