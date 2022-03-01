package com.app.data

import androidx.paging.*
import com.app.common.DB_PAGE_SIZE
import com.app.common.DataState
import com.app.common.LIKED
import com.app.common.models.CommunityMemberLike
import com.app.common.utils.Misc
import com.app.data.model.RemoteKeys
import com.app.data.repository.CommunityLocal
import com.app.data.repository.CommunityRemote
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.lang.Exception

@OptIn(ExperimentalPagingApi::class)
@RunWith(JUnit4::class)
class CommunityRemoteMediatorTest {

    @MockK
    lateinit var mLocal: CommunityLocal

    @MockK
    lateinit var mRemote: CommunityRemote

    private lateinit var communityRemoteMediator: CommunityRemoteMediator

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        communityRemoteMediator = CommunityRemoteMediator(mLocal, mRemote)
    }

    @After
    fun tearDown() {

    }

    @Test
    fun `test refresh load Returns Success Result When More Data Is Present`() = runBlocking {
        //Given
        val dummyData = (1..20).map {
            getDummyCommunityMemberObject()
        }

        coEvery { mRemote.fetchCommunity(any()) }.returns(DataState.success(dummyData))
        coEvery { mLocal.addCommunityDataIntoDatabase(any(),any(),any(),any()) }.returns(Unit)

        val pagingState = PagingState<Int, CommunityMemberLike>(
            listOf(),
            null,
            PagingConfig(DB_PAGE_SIZE),
            10
        )

        //Invoke
        val result = communityRemoteMediator.load(LoadType.REFRESH, pagingState)

        //Then
        MatcherAssert.assertThat(result, CoreMatchers.instanceOf(RemoteMediator.MediatorResult.Success::class.java))
        MatcherAssert.assertThat(false, CoreMatchers.`is`((result as RemoteMediator.MediatorResult.Success).endOfPaginationReached))
    }

    @Test
    fun `test append load Returns Success Result When More Data Is Present`() = runBlocking {
        //Given
        val dummyData = (1..20).map {
            getDummyCommunityMemberObject()
        }

        coEvery { mRemote.fetchCommunity(any()) }.returns(DataState.success(dummyData))
        coEvery { mLocal.remoteKeysId(any()) }.returns(RemoteKeys(Misc.getRandomLong(),Misc.getRandomInt()))
        coEvery { mLocal.addCommunityDataIntoDatabase(any(),any(),any(),any()) }.returns(Unit)

        val pagingState = PagingState(
            getListOfPagingSource(),
            null,
            PagingConfig(DB_PAGE_SIZE),
            10
        )

        //Invoke
        val result = communityRemoteMediator.load(LoadType.APPEND, pagingState)

        //Then
        MatcherAssert.assertThat(result, CoreMatchers.instanceOf(RemoteMediator.MediatorResult.Success::class.java))
        MatcherAssert.assertThat(false, CoreMatchers.`is`((result as RemoteMediator.MediatorResult.Success).endOfPaginationReached))
    }



    @Test
    fun `test append load Returns Success Result When More Data Is Not Present`() = runBlocking {
        //Given
        val dummyData = (1..15).map {
            getDummyCommunityMemberObject()
        }

        coEvery { mRemote.fetchCommunity(any()) }.returns(DataState.success(dummyData))
        coEvery { mLocal.remoteKeysId(any()) }.returns(RemoteKeys(Misc.getRandomLong(),Misc.getRandomInt()))
        coEvery { mLocal.addCommunityDataIntoDatabase(any(),any(),any(),any()) }.returns(Unit)

        val pagingState = PagingState(
            getListOfPagingSource(),
            null,
            PagingConfig(DB_PAGE_SIZE),
            10
        )

        //Invoke
        val result = communityRemoteMediator.load(LoadType.APPEND, pagingState)

        //Then
        MatcherAssert.assertThat(result, CoreMatchers.instanceOf(RemoteMediator.MediatorResult.Success::class.java))
        MatcherAssert.assertThat(true, CoreMatchers.`is`((result as RemoteMediator.MediatorResult.Success).endOfPaginationReached))
    }

    @Test
    fun `test refresh load returns Error Result When Server does not respond`() = runBlocking {
        //Given
        val error = "Something went wrong"
        coEvery { mRemote.fetchCommunity(any()) }.returns(DataState.error(error))
        coEvery { mLocal.addCommunityDataIntoDatabase(any(),any(),any(),any()) }.returns(Unit)

        val pagingState = PagingState<Int, CommunityMemberLike>(
            listOf(),
            null,
            PagingConfig(DB_PAGE_SIZE),
            10
        )

        //Invoke
        val result = communityRemoteMediator.load(LoadType.REFRESH, pagingState)

        //Then
        MatcherAssert.assertThat(result, CoreMatchers.instanceOf(RemoteMediator.MediatorResult.Error::class.java))
        MatcherAssert.assertThat(error, CoreMatchers.`is`((result as RemoteMediator.MediatorResult.Error).throwable.message))
    }


    @Test
    fun `test load returns Exception`() = runBlocking {
        //Given
        val error = "Something went wrong"
        coEvery { mRemote.fetchCommunity(any()) }.throws(Exception(error))

        val pagingState = PagingState<Int, CommunityMemberLike>(
            listOf(),
            null,
            PagingConfig(DB_PAGE_SIZE),
            10
        )

        //Invoke
        val result = communityRemoteMediator.load(LoadType.REFRESH, pagingState)

        //Then
        MatcherAssert.assertThat(result, CoreMatchers.instanceOf(RemoteMediator.MediatorResult.Error::class.java))
        MatcherAssert.assertThat(error, CoreMatchers.`is`((result as RemoteMediator.MediatorResult.Error).throwable.message))
    }

    private fun getListOfPagingSource(): ArrayList<PagingSource.LoadResult.Page<Int, CommunityMemberLike>> {
        val list : ArrayList<PagingSource.LoadResult.Page<Int,CommunityMemberLike>> = ArrayList()
        list.add(PagingSource.LoadResult.Page((1..20).map { getDummyCommunityMemberObject() },null,2))
        return list
    }

    private fun getDummyCommunityMemberObject() = CommunityMemberLike(
        Misc.getRandomLong(),
        Misc.generateRandomString(),
        Misc.generateRandomString(),
        Misc.generateRandomString(),
        (1..5).map { Misc.generateRandomString() },
        (1..5).map { Misc.generateRandomString() },
        Misc.getRandomInt(),
        LIKED
    )
}