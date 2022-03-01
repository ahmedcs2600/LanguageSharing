package com.app.remote.core

import com.app.remote.Utils
import com.app.remote.models.CommunityMemberResponse
import okhttp3.ResponseBody.Companion.toResponseBody
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Response

@RunWith(JUnit4::class)
class ApiResponseTest {

    @Test
    fun `test ApiResponse returns ApiSuccessResponse`() {
        val dummyData = Utils.getDummyCommunityResponse()
        val result = ApiResponse.create(response = Response.success(dummyData))
        MatcherAssert.assertThat(
            result,
            CoreMatchers.instanceOf(ApiResponse.ApiSuccessResponse::class.java)
        )
        MatcherAssert.assertThat(
            (result as ApiResponse.ApiSuccessResponse).data,
            CoreMatchers.`is`(dummyData)
        )
    }

    @Test
    fun `test ApiResponse returns ApiFailureResponse Error`() {
        val result = ApiResponse.create(
            response = Response.error<CommunityMemberResponse>(
                500,
                "\"errorCode\": null,\n \"type\": \"success\"".toResponseBody()
            )
        )
        MatcherAssert.assertThat(
            result,
            CoreMatchers.instanceOf(ApiResponse.ApiFailureResponse.Error::class.java)
        )
    }
}