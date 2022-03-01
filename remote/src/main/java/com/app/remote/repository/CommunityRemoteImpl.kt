package com.app.remote.repository

import com.app.common.DataState
import com.app.common.exceptions.ResponseBodyNotFound
import com.app.common.models.BaseCommunityMember
import com.app.data.repository.CommunityRemote
import com.app.remote.core.ApiResponse
import com.app.remote.core.ApiService
import com.app.remote.message
import javax.inject.Inject

class CommunityRemoteImpl @Inject constructor(
    private val apiService: ApiService
) : CommunityRemote {

    override suspend fun fetchCommunity(pageDetails: String): DataState<List<BaseCommunityMember>> {
        val response = apiService.fetchCommunity(pageDetails)
        return when (val res = ApiResponse.create(response = response)) {
            is ApiResponse.ApiSuccessResponse -> DataState.success(
                res.data?.response
                    ?: throw ResponseBodyNotFound("Response Body Not Found")
            )
            is ApiResponse.ApiFailureResponse.Error -> DataState.error(res.message())
            is ApiResponse.ApiFailureResponse.Exception -> DataState.error("Something went wrong")
        }
    }
}