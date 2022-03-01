package com.app.data.repository

import androidx.lifecycle.LiveData
import androidx.paging.*
import com.app.common.DB_PAGE_SIZE
import com.app.common.models.CommunityMemberLike
import com.app.data.CommunityRemoteMediator
import com.app.domain.repository.CommunityMemberRepository
import javax.inject.Inject

class CommunityMemberDataRepository @Inject constructor(
    private val local : CommunityLocal,
    private val mCommunityRemoteMediator : CommunityRemoteMediator
) : CommunityMemberRepository {

    override fun fetchCommunity(): LiveData<PagingData<CommunityMemberLike>> {
        val pagingSourceFactory = {
            local.getCommunityMembers()
        }

        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(
                pageSize = DB_PAGE_SIZE,
                prefetchDistance = DB_PAGE_SIZE / 2
            ),
            remoteMediator = mCommunityRemoteMediator,
            pagingSourceFactory = pagingSourceFactory
        ).liveData
    }

    override suspend fun setLike(id: Long, isLike: Int) {
        local.setLike(id,isLike)
    }
}