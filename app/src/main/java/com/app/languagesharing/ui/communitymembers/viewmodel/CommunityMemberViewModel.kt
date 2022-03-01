package com.app.languagesharing.ui.communitymembers.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.liveData
import com.app.common.models.CommunityMemberLike
import com.app.domain.interactors.FetchCommunityUsecase
import com.app.domain.interactors.LikeDisLikeUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CommunityMemberViewModel @Inject constructor(
    private val mFetchCommunityUsecase: FetchCommunityUsecase,
    private val mLikeDisLikeUsecase: LikeDisLikeUsecase,
) : ViewModel() {

    val communityMembers : LiveData<PagingData<CommunityMemberLike>>
    get() = mFetchCommunityUsecase.invoke().cachedIn(viewModelScope)

    fun setLike(id: Long, isLike: Int) {
        viewModelScope.launch {
            mLikeDisLikeUsecase.invoke(id,isLike)
        }
    }
}