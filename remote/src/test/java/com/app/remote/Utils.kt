package com.app.remote

import com.app.common.utils.Misc
import com.app.remote.models.CommunityMember
import com.app.remote.models.CommunityMemberResponse
import java.util.*
import kotlin.random.Random

object Utils {

    fun getEmptyDummyCommunityResponse(): CommunityMemberResponse {
        return CommunityMemberResponse(
            listOf(),
            null,
            "success"
        )
    }

    fun getDummyCommunityResponse(): CommunityMemberResponse {
        return CommunityMemberResponse(
            getDummyCommunityMembers(),
            null,
            "success"
        )
    }

    private fun getDummyCommunityMembers(): List<CommunityMember> {
        return (1..10).map { getRandomCommunityMember() }
    }

    private fun getRandomCommunityMember(): CommunityMember {
        return CommunityMember(
            Misc.getRandomLong(),
            Misc. generateRandomString(),
            Misc. generateRandomString(),
            Misc. generateRandomString(),
            (1..5).map { Misc.generateRandomString() },
            (1..5).map { Misc.generateRandomString() },
            Misc.getRandomInt()
        )
    }
}