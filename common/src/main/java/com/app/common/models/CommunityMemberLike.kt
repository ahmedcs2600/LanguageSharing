package com.app.common.models

import com.app.common.LIKED

data class CommunityMemberLike(
    override val id: Long,
    override val topic: String,
    override val firstName: String,
    override val pictureUrl: String,
    override val natives: List<String>,
    override val learns: List<String>,
    override val referenceCnt: Int,
    val isLiked : Int
) : BaseCommunityMember {
    fun natives() = natives.joinToString(",")

    fun learns() = learns.joinToString(",")

    val isNewMember: Boolean get() = referenceCnt <= 0

    fun hasLiked() = isLiked == LIKED
}