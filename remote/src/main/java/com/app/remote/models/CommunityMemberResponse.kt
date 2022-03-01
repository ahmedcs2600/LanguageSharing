package com.app.remote.models

import com.google.gson.annotations.SerializedName

data class CommunityMemberResponse(
    @field:SerializedName("response") val response: List<CommunityMember>,
    @field:SerializedName("error") val error: Any?,
    @field:SerializedName("type") val type: String,
) {
    val isSuccess : Boolean get() = type == "success"
}