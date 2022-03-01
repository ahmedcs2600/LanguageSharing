package com.app.remote.core

import com.app.remote.models.CommunityMemberResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("api/{pageDetails}")
    suspend fun fetchCommunity(@Path("pageDetails") pageDetails : String) : Response<CommunityMemberResponse>
}