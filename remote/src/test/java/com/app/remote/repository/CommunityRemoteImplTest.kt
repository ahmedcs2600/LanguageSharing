package com.app.remote.repository

import com.app.common.DataState
import com.app.common.utils.toCommunityPageUrl
import com.app.remote.Utils
import com.app.remote.core.ApiService
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
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
class CommunityRemoteImplTest {

    @MockK
    lateinit var mApiService: ApiService

    lateinit var repository: CommunityRemoteImpl

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        repository = CommunityRemoteImpl(mApiService)
    }

    @After
    fun tearDown() {
    }

    @Test
    fun `test fetchCommunity() returns list of Community Members`() = runBlocking {
        //Given
        val dummyData = Utils.getDummyCommunityResponse()
        coEvery { mApiService.fetchCommunity(any()) }.returns(Response.success(dummyData))

        //Invoke
        val result = repository.fetchCommunity(1.toCommunityPageUrl)

        //Then
        MatcherAssert.assertThat(result, CoreMatchers.notNullValue())
        MatcherAssert.assertThat(result, CoreMatchers.instanceOf(DataState.Success::class.java))
        MatcherAssert.assertThat(
            (result as DataState.Success).data,
            CoreMatchers.`is`(dummyData.response)
        )
    }

    @Test
    fun `test fetchCommunity() returns empty records`() = runBlocking {
        //Given
        val dummyData = Utils.getEmptyDummyCommunityResponse()
        coEvery { mApiService.fetchCommunity(any()) }.returns(Response.success(dummyData))

        //Invoke
        val result = repository.fetchCommunity(1.toCommunityPageUrl)

        //Then
        MatcherAssert.assertThat(result, CoreMatchers.notNullValue())
        MatcherAssert.assertThat(result, CoreMatchers.instanceOf(DataState.Success::class.java))
        MatcherAssert.assertThat((result as DataState.Success).data, CoreMatchers.`is`(dummyData.response))
    }

    @Test
    fun `test fetchCommunity() returns error`() = runBlocking {
        //Given
        coEvery { mApiService.fetchCommunity(any()) }.returns(Response.error(
            403,
            "\"errorCode\": null,\n \"type\": \"success\"".toResponseBody()
        ))

        //Invoke
        val result = repository.fetchCommunity(1.toCommunityPageUrl)

        //Then
        MatcherAssert.assertThat(result, CoreMatchers.notNullValue())
        MatcherAssert.assertThat(result, CoreMatchers.instanceOf(DataState.Error::class.java))
    }
}