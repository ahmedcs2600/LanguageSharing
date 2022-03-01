package com.app.common.models

interface BaseCommunityMember {
    val id: Long
    val topic: String
    val firstName: String
    val pictureUrl: String
    val natives: List<String>
    val learns: List<String>
    val referenceCnt: Int
}