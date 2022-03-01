package com.app.common.models

import com.app.common.LIKED
import com.app.common.UN_LIKED
import com.app.common.utils.Misc
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class CommunityMemberLikeTest {

    @Test
    fun `test invokes natives() and learns() returns string with comma separation`() {
        val obj = getDummyCommunityMemberObject()
        MatcherAssert.assertThat(obj.natives(),CoreMatchers.`is`(obj.natives.joinToString(",")))
        MatcherAssert.assertThat(obj.learns(),CoreMatchers.`is`(obj.learns.joinToString(",")))
    }

    @Test
    fun `test reference count 0 supposed to be the new member`() {
        val obj = CommunityMemberLike(
            Misc.getRandomLong(),
            Misc.generateRandomString(),
            Misc.generateRandomString(),
            Misc.generateRandomString(),
            (1..5).map { Misc.generateRandomString() },
            (1..5).map { Misc.generateRandomString() },
            0,
            LIKED
        )

        MatcherAssert.assertThat(true, CoreMatchers.`is`(obj.isNewMember))
    }

    @Test
    fun `test reference count greater than zero is not supposed to be the new member`() {
        val obj = CommunityMemberLike(
            Misc.getRandomLong(),
            Misc.generateRandomString(),
            Misc.generateRandomString(),
            Misc.generateRandomString(),
            (1..5).map { Misc.generateRandomString() },
            (1..5).map { Misc.generateRandomString() },
            5,
            LIKED
        )

        MatcherAssert.assertThat(false, CoreMatchers.`is`(obj.isNewMember))
    }

    @Test
    fun `test isLiked = UN_LIKED returns hasLiked() false`() {
        val obj = CommunityMemberLike(
            Misc.getRandomLong(),
            Misc.generateRandomString(),
            Misc.generateRandomString(),
            Misc.generateRandomString(),
            (1..5).map { Misc.generateRandomString() },
            (1..5).map { Misc.generateRandomString() },
            5,
            UN_LIKED
        )

        MatcherAssert.assertThat(false, CoreMatchers.`is`(obj.hasLiked()))
    }

    @Test
    fun `test isLiked = LIKED returns hasLiked() true`() {
        val obj = CommunityMemberLike(
            Misc.getRandomLong(),
            Misc.generateRandomString(),
            Misc.generateRandomString(),
            Misc.generateRandomString(),
            (1..5).map { Misc.generateRandomString() },
            (1..5).map { Misc.generateRandomString() },
            5,
            LIKED
        )

        MatcherAssert.assertThat(true, CoreMatchers.`is`(obj.hasLiked()))
    }

    private fun getDummyCommunityMemberObject() = CommunityMemberLike(
        Misc.getRandomLong(),
        Misc.generateRandomString(),
        Misc.generateRandomString(),
        Misc.generateRandomString(),
        (1..5).map { Misc.generateRandomString() },
        (1..5).map { Misc.generateRandomString() },
        Misc.getRandomInt(),
        LIKED
    )
}