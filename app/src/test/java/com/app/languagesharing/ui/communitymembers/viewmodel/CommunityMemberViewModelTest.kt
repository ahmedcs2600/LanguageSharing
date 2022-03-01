package com.app.languagesharing.ui.communitymembers.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagingData
import com.app.common.LIKED
import com.app.common.models.CommunityMemberLike
import com.app.common.utils.Misc
import com.app.domain.interactors.FetchCommunityUsecase
import com.app.domain.interactors.LikeDisLikeUsecase
import com.app.languagesharing.MainCoroutinesRule
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class CommunityMemberViewModelTest {

    @MockK
    lateinit var mFetchCommunityUsecase: FetchCommunityUsecase

    @get:Rule
    var coroutinesRule = MainCoroutinesRule()

    @MockK
    lateinit var mLikeDisLikeUsecase: LikeDisLikeUsecase

    lateinit var mViewModel: CommunityMemberViewModel

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @After
    fun tearDown() {
    }

    @Test
    fun `test invoking communityMembers invokes mFetchCommunityUsecase`() {
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

        coEvery { mFetchCommunityUsecase.invoke() }.returns(MutableLiveData(paging))

        val pagingObserver = mockk<Observer<PagingData<CommunityMemberLike>>>(relaxed = true)

        //Invoke
        mViewModel = CommunityMemberViewModel(mFetchCommunityUsecase, mLikeDisLikeUsecase)
        mViewModel.communityMembers.observeForever(pagingObserver)

        //Then
        coVerify(exactly = 1) { mFetchCommunityUsecase.invoke() }
    }

    @Test
    fun `test invoke setLike() invokes mLikeDisLikeUsecase()`() = runBlocking {

        //Given
        coEvery { mLikeDisLikeUsecase.invoke(1, LIKED) }.returns(Unit)
        mViewModel = CommunityMemberViewModel(mFetchCommunityUsecase, mLikeDisLikeUsecase)


        //Invoke
        mViewModel.setLike(1, LIKED)

        //Then
        coVerify(exactly = 1) { mLikeDisLikeUsecase.invoke(1, LIKED) }
    }
}