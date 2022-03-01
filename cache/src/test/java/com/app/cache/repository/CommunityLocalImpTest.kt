package com.app.cache.repository

import androidx.paging.LoadType
import androidx.room.withTransaction
import com.app.cache.AppDatabase
import com.app.cache.R
import com.app.cache.dao.CommunityMemberDao
import com.app.cache.dao.MemberLikeDao
import com.app.cache.dao.RemoteKeysDao
import com.app.cache.entity.CommunityMember
import com.app.cache.entity.RemoteKeys
import com.app.cache.mapper.CommunityMemberMapper
import com.app.cache.mapper.RemoteKeysMapper
import com.app.common.LIKED
import com.app.common.UN_LIKED
import com.app.common.utils.Misc
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import com.app.data.model.RemoteKeys as RemoteDataKey

@RunWith(JUnit4::class)
class CommunityLocalImpTest {

    @MockK
    lateinit var mCommunityMemberDao: CommunityMemberDao

    @MockK
    lateinit var mRemoteKeysDao: RemoteKeysDao

    @MockK
    lateinit var mMemberLikeDao: MemberLikeDao

    @MockK
    lateinit var database: AppDatabase

    @MockK
    lateinit var mCommunityMemberMapper: CommunityMemberMapper

    @MockK
    lateinit var mRemoteKeysMapper: RemoteKeysMapper

    lateinit var mCommunityLocalImp: CommunityLocalImp

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        mCommunityLocalImp = CommunityLocalImp(
            mCommunityMemberDao,
            mRemoteKeysDao,
            mMemberLikeDao,
            database,
            mCommunityMemberMapper,
            mRemoteKeysMapper
        )

        mockkStatic(
            "androidx.room.RoomDatabaseKt"
        )
    }

    @After
    fun tearDown() {

    }

    @Test
    fun `test getCommunityMembers() returns PagingSource of CommunityMemberLike`() {
        //Given
        coEvery { mCommunityMemberDao.getCommunityMembers() }.returns(PagingSourceUtils(listOf()))
        //Invoke
        mCommunityLocalImp.getCommunityMembers()
        //Then
        coVerify(exactly = 1) { mCommunityMemberDao.getCommunityMembers() }
    }

    @Test
    fun `test remoteKeysId() returns null`() = runBlocking {
        //Given
        coEvery { mRemoteKeysDao.remoteKeysId(any()) }.returns(null)

        //Invoke
        val result = mCommunityLocalImp.remoteKeysId(Misc.getRandomLong())

        //Then
        MatcherAssert.assertThat(result, CoreMatchers.nullValue())
    }

    @Test
    fun `test remoteKeysId() returns RemoteKeys`() = runBlocking {
        //Given
        val keys = RemoteKeys(Misc.getRandomLong(), Misc.getRandomInt())
        val remoteDataKey = RemoteDataKey(Misc.getRandomLong(), Misc.getRandomInt())
        coEvery { mRemoteKeysDao.remoteKeysId(any()) }.returns(keys)
        coEvery { mRemoteKeysMapper.mapTo(any()) }.returns(remoteDataKey)

        //Invoke
        val result = mCommunityLocalImp.remoteKeysId(Misc.getRandomLong())

        //Then
        MatcherAssert.assertThat(result, CoreMatchers.notNullValue())
        MatcherAssert.assertThat(result, CoreMatchers.`is`(remoteDataKey))
    }

    @Test
    fun `test setLike() should invoke deleteById method`() = runBlocking {

        //Given
        coEvery { mMemberLikeDao.deleteById(any()) }.returns(Unit)

        //Invoke
        mCommunityLocalImp.setLike(Misc.getRandomLong(), LIKED)

        //Then
        coVerify(exactly = 1) { mMemberLikeDao.deleteById(any()) }
    }


    @Test
    fun `test setLike() should invoke insert method`() = runBlocking {

        //Given
        coEvery { mMemberLikeDao.insert(any()) }.returns(Unit)

        //Invoke
        mCommunityLocalImp.setLike(Misc.getRandomLong(), UN_LIKED)

        //Then
        coVerify(exactly = 1) { mMemberLikeDao.insert(any()) }
    }

    @Test
    fun `test invoke addCommunityDataIntoDatabase() flush all records and insert Data in Database`() = runBlocking {
        //Given
        val dummyList = (1..5).map {
            CommunityMember(
                Misc.getRandomLong(),
                Misc.generateRandomString(),
                Misc.generateRandomString(),
                Misc.generateRandomString(),
                (1..5).map { Misc.generateRandomString() },
                (1..5).map { Misc.generateRandomString() },
                Misc.getRandomInt()
            )
        }

        val dummyCommunityMember = CommunityMember(
            Misc.getRandomLong(),
            Misc.generateRandomString(),
            Misc.generateRandomString(),
            Misc.generateRandomString(),
            (1..5).map { Misc.generateRandomString() },
            (1..5).map { Misc.generateRandomString() },
            Misc.getRandomInt()
        )

        coEvery { mCommunityMemberDao.deleteAll() }.returns(Unit)
        coEvery { mRemoteKeysDao.clearRemoteKeys() }.returns(Unit)
        coEvery { mRemoteKeysDao.insertAll(any()) }.returns(Unit)
        coEvery { mCommunityMemberDao.insertAll(any()) }.returns(Unit)
        coEvery { mCommunityMemberMapper.mapTo(any()) }.returns(dummyCommunityMember)

        val transactionLambda = slot<suspend () -> R>()
        coEvery { database.withTransaction(capture(transactionLambda)) } coAnswers {
            transactionLambda.captured.invoke()
        }

        //Invoke
        mCommunityLocalImp.addCommunityDataIntoDatabase(dummyList, 2, null, LoadType.REFRESH)

        coVerify(exactly = 1) { mCommunityMemberDao.deleteAll() }
        coVerify(exactly = 1) { mRemoteKeysDao.clearRemoteKeys() }
    }

    @Test
    fun `test invoke addCommunityDataIntoDatabase() do not flush records and `() = runBlocking {
        //Given
        val dummyList = (1..5).map {
            CommunityMember(
                Misc.getRandomLong(),
                Misc.generateRandomString(),
                Misc.generateRandomString(),
                Misc.generateRandomString(),
                (1..5).map { Misc.generateRandomString() },
                (1..5).map { Misc.generateRandomString() },
                Misc.getRandomInt()
            )
        }

        val dummyCommunityMember = CommunityMember(
            Misc.getRandomLong(),
            Misc.generateRandomString(),
            Misc.generateRandomString(),
            Misc.generateRandomString(),
            (1..5).map { Misc.generateRandomString() },
            (1..5).map { Misc.generateRandomString() },
            Misc.getRandomInt()
        )

        coEvery { mCommunityMemberDao.deleteAll() }.returns(Unit)
        coEvery { mRemoteKeysDao.clearRemoteKeys() }.returns(Unit)
        coEvery { mRemoteKeysDao.insertAll(any()) }.returns(Unit)
        coEvery { mCommunityMemberDao.insertAll(any()) }.returns(Unit)
        coEvery { mCommunityMemberMapper.mapTo(any()) }.returns(dummyCommunityMember)

        val transactionLambda = slot<suspend () -> R>()
        coEvery { database.withTransaction(capture(transactionLambda)) } coAnswers {
            transactionLambda.captured.invoke()
        }

        //Invoke
        mCommunityLocalImp.addCommunityDataIntoDatabase(dummyList, 21, 20, LoadType.PREPEND)

        coVerify(exactly = 0) { mCommunityMemberDao.deleteAll() }
        coVerify(exactly = 0) { mRemoteKeysDao.clearRemoteKeys() }
    }
}