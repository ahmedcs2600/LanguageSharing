package com.app.remote.models

import com.app.common.models.BaseCommunityMember
import com.google.gson.annotations.SerializedName

data class CommunityMember(
    @field:SerializedName("id") override val id: Long,
    @field:SerializedName("topic") override val topic: String,
    @field:SerializedName("firstName") override val firstName: String,
    @field:SerializedName("pictureUrl") override val pictureUrl: String,
    @field:SerializedName("natives") override val natives: List<String>,
    @field:SerializedName("learns") override val learns: List<String>,
    @field:SerializedName("referenceCnt") override val referenceCnt: Int,
) : BaseCommunityMember