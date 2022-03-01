package com.app.cache.mapper

import com.app.cache.entity.CommunityMember
import com.app.common.mapper.Mapper
import com.app.common.models.BaseCommunityMember
import javax.inject.Inject

class CommunityMemberMapper @Inject constructor() : Mapper<BaseCommunityMember, CommunityMember> {

    override fun mapTo(input: BaseCommunityMember): CommunityMember {
        return CommunityMember(
            input.id,
            input.topic,
            input.firstName,
            input.pictureUrl,
            input.natives,
            input.learns,
            input.referenceCnt
        )
    }
}