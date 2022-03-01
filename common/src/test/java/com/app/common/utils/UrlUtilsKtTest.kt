package com.app.common.utils

import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class UrlUtilsKtTest {

    @Test
    fun `test toCommunityPageUrl returns page Details`() {
        val page = 1
        val dynamicUrl = page.toCommunityPageUrl
        MatcherAssert.assertThat("community_1.json",CoreMatchers.`is`(dynamicUrl))
    }

}