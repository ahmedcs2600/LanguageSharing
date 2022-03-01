package com.app.domain.repository

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingData
import com.app.common.models.CommunityMemberLike

/**
 * CommunityMemberRepository is an interface data layer for handling communication with any data source such as Database or Network
 * @see [CommunityMemberDataRepository] for the Implementation
 * */
interface CommunityMemberRepository {

    fun fetchCommunity(): LiveData<PagingData<CommunityMemberLike>>

    suspend fun setLike(id : Long, isLike : Int)
}