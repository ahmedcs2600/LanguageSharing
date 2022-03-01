package com.app.cache.repository

import androidx.paging.LoadType
import androidx.paging.PagingSource
import androidx.room.withTransaction
import com.app.cache.AppDatabase
import com.app.cache.dao.CommunityMemberDao
import com.app.cache.dao.MemberLikeDao
import com.app.cache.dao.RemoteKeysDao
import com.app.cache.entity.MemberLike
import com.app.cache.entity.RemoteKeys
import com.app.cache.mapper.CommunityMemberMapper
import com.app.cache.mapper.RemoteKeysMapper
import com.app.common.LIKED
import com.app.common.models.BaseCommunityMember
import com.app.common.models.CommunityMemberLike
import com.app.data.repository.CommunityLocal
import javax.inject.Inject

class CommunityLocalImp @Inject constructor(
    private val mCommunityMemberDao: CommunityMemberDao,
    private val mRemoteKeysDao: RemoteKeysDao,
    private val mMemberLikeDao: MemberLikeDao,
    private val database: AppDatabase,
    private val mCommunityMemberMapper: CommunityMemberMapper,
    private val mRemoteKeysMapper: RemoteKeysMapper,
) :
    CommunityLocal {

    override fun getCommunityMembers(): PagingSource<Int, CommunityMemberLike> {
        return mCommunityMemberDao.getCommunityMembers()
    }

    override suspend fun remoteKeysId(id: Long): com.app.data.model.RemoteKeys? {
        val key = mRemoteKeysDao.remoteKeysId(id) ?: return null
        return mRemoteKeysMapper.mapTo(key)
    }

    override suspend fun addCommunityDataIntoDatabase(
        result: List<BaseCommunityMember>,
        nextKey: Int?,
        prevPageLastItemId: Long?,
        loadType: LoadType
    ) {

        database.withTransaction {
            if (loadType == LoadType.REFRESH) {
                mCommunityMemberDao.deleteAll()
                mRemoteKeysDao.clearRemoteKeys()
            }

            val keys = result.map { RemoteKeys(it.id, nextKey) }
            mRemoteKeysDao.insertAll(keys)

            mCommunityMemberDao.insertAll(result.map { mCommunityMemberMapper.mapTo(it) })
        }
    }

    override suspend fun setLike(id: Long, isLike: Int) {
        if (isLike == LIKED) mMemberLikeDao.deleteById(id)
        else mMemberLikeDao.insert(MemberLike(id, LIKED))
    }
}