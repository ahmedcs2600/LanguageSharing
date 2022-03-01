package com.app.domain.interactors

import androidx.lifecycle.MutableLiveData
import androidx.paging.PagingData
import com.app.common.LIKED
import com.app.common.models.CommunityMemberLike
import com.app.common.utils.Misc
import com.app.domain.repository.CommunityMemberRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class FetchCommunityUsecaseTest {

    @MockK
    lateinit var repository: CommunityMemberRepository

    lateinit var usecase: FetchCommunityUsecase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        usecase = FetchCommunityUsecase(repository)
    }

    @After
    fun tearDown() {
    }

    @Test
    fun `test invoking FetchCommunityUsecase returns live data paging list`() {
        //Given
        val dummyList = (1..5).map {
            CommunityMemberLike(
                Misc.getRandomLong(),
                Misc.generateRandomString(),
                Misc.generateRandomString(),
                Misc.generateRandomString(),
                (1..5).map { Misc.generateRandomString() },
                (1..5).map { Misc.generateRandomString() },
                Misc.getRandomInt(), LIKED
            )
        }
        val paging = PagingData.from(dummyList)
        coEvery { repository.fetchCommunity() }.returns(MutableLiveData(paging))

        //Invoke
        val result = usecase.invoke()

        //Then
        MatcherAssert.assertThat(result,CoreMatchers.notNullValue())
        MatcherAssert.assertThat(result.value,CoreMatchers.instanceOf(PagingData::class.java))
        MatcherAssert.assertThat(result.value,CoreMatchers.`is`(paging))
    }
}