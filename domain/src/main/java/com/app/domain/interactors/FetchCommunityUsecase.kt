package com.app.domain.interactors

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingData
import com.app.common.models.CommunityMemberLike
import com.app.domain.repository.CommunityMemberRepository
import javax.inject.Inject

/**
 * A use-case to load the Communities Member From [CommunityMemberRepository]
 * @see [CommunityMemberRepository] For More Information
 */
class FetchCommunityUsecase @Inject constructor(private val repository : CommunityMemberRepository) {
    fun invoke() : LiveData<PagingData<CommunityMemberLike>> {
        return repository.fetchCommunity()
    }
}