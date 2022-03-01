package com.app.data.repository

import com.app.common.DataState
import com.app.common.models.BaseCommunityMember

interface CommunityRemote {
    suspend fun fetchCommunity(pageDetails : String) : DataState<List<BaseCommunityMember>>
}