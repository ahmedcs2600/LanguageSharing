package com.app.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.app.common.DataState
import com.app.common.NETWORK_PAGE_SIZE
import com.app.common.STARTING_PAGE_INDEX
import com.app.common.models.CommunityMemberLike
import com.app.common.utils.toCommunityPageUrl
import com.app.data.model.RemoteKeys
import com.app.data.repository.CommunityLocal
import com.app.data.repository.CommunityRemote
import java.io.IOException
import java.lang.Exception
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class CommunityRemoteMediator @Inject constructor(
    private val local: CommunityLocal,
    private val remote: CommunityRemote
): RemoteMediator<Int, CommunityMemberLike>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, CommunityMemberLike>
    ): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> {
                STARTING_PAGE_INDEX
            }
            LoadType.PREPEND -> {
                // Since we do not to paginate in other direction
                return MediatorResult.Success(endOfPaginationReached = true)
            }
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                val nextKey = remoteKeys?.nextKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                nextKey
            }
        }
        try {
            return when(val res = remote.fetchCommunity(page.toCommunityPageUrl)) {
                is DataState.Error -> {
                    MediatorResult.Error(Exception(res.message))
                }
                is DataState.Success -> {
                    val data = res.data
                    val endOfPaginationReached = data.size < NETWORK_PAGE_SIZE
                    val nextKey = if (endOfPaginationReached) null else page + 1
                    val prevPageLastItemId = getIdForFirstItem(state)
                    local.addCommunityDataIntoDatabase(data,nextKey,prevPageLastItemId,loadType)
                    MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
                }
            }
        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: Exception) {
            return MediatorResult.Error(exception)
        }
    }

    private fun getIdForFirstItem(state: PagingState<Int, CommunityMemberLike>): Long? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()?.id
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, CommunityMemberLike>): RemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { item ->
                local.remoteKeysId(item.id)
            }
    }
}