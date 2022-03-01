package com.app.cache.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "member_like")
data class MemberLike(
    @PrimaryKey val id: Long,
    val isLiked: Int
)
