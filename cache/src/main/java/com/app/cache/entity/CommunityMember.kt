package com.app.cache.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.app.common.models.BaseCommunityMember

@Entity(tableName = "community_member")
data class CommunityMember(
    @PrimaryKey override val id: Long,
    override val topic: String,
    override val firstName: String,
    override val pictureUrl: String,
    override val natives: List<String>,
    override val learns: List<String>,
    override val referenceCnt: Int,
) : BaseCommunityMember