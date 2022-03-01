package com.app.domain.interactors

import com.app.common.LIKED
import com.app.common.utils.Misc
import com.app.domain.repository.CommunityMemberRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test

class LikeDisLikeUsecaseTest {

    @MockK
    lateinit var repository: CommunityMemberRepository

    lateinit var usecase: LikeDisLikeUsecase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        usecase = LikeDisLikeUsecase(repository)
    }

    @After
    fun tearDown() {
    }

    @Test
    fun `test invoking LikeDisLikeUsecase invokes CommunityMemberRepository setLike method`() = runBlocking {
        //Given
        val id = Misc.getRandomLong()
        val isLike = LIKED

        coEvery { repository.setLike(id,isLike) }.returns(Unit)
        //Invoke
        usecase.invoke(id,isLike)

        //Then
        coVerify(exactly = 1) { repository.setLike(id,isLike) }
    }
}