package com.app.data.repository

import androidx.paging.LoadType
import androidx.paging.PagingSource
import com.app.common.models.BaseCommunityMember
import com.app.common.models.CommunityMemberLike
import com.app.data.model.RemoteKeys

interface CommunityLocal {
    fun getCommunityMembers() : PagingSource<Int, CommunityMemberLike>

    suspend fun remoteKeysId(id: Long): RemoteKeys?

    suspend fun addCommunityDataIntoDatabase(
        result: List<BaseCommunityMember>,
        nextKey: Int?,
        prevPageLastItemId: Long?,
        loadType: LoadType
    )

    suspend fun setLike(id: Long, isLike: Int)
}