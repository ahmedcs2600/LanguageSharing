package com.app.domain.interactors

import com.app.domain.repository.CommunityMemberRepository
import javax.inject.Inject

/**
 * A use-case set Update Like Status
 * @see [CommunityMemberRepository] For More Information
 */
class LikeDisLikeUsecase @Inject constructor(private val repository: CommunityMemberRepository) {
    suspend fun invoke(id: Long, isLike: Int) {
        repository.setLike(id, isLike)
    }
}