package com.app.data.repository

import com.app.common.LIKED
import com.app.common.utils.Misc
import com.app.data.CommunityRemoteMediator
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class CommunityMemberDataRepositoryTest {

    @MockK
    lateinit var mLocal: CommunityLocal

    @MockK(relaxed = true)
    lateinit var mCommunityRemoteMediator: CommunityRemoteMediator

    private lateinit var mCommunityMemberDataRepository: CommunityMemberDataRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        mCommunityMemberDataRepository =
            CommunityMemberDataRepository(mLocal, mCommunityRemoteMediator)
    }

    @Test
    fun `test invoke setLike() invokes local setLike`() = runBlocking {
        //Given
        val id = Misc.getRandomLong()
        val status = LIKED
        coEvery { mLocal.setLike(id, status) }.returns(Unit)

        //Invoke
        mCommunityMemberDataRepository.setLike(id, status)

        //Then
        coVerify(exactly = 1) { mLocal.setLike(id, status) }
    }

    @After
    fun tearDown() {

    }

}