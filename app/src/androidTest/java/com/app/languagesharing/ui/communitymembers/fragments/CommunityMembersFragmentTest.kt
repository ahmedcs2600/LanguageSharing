package com.app.languagesharing.ui.communitymembers.fragments

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.app.languagesharing.R
import com.app.languagesharing.launchFragmentInHiltContainer
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CommunityMembersFragmentTest {

    @Test
    fun test_CommunityMembersFragment_is_visible() {
        launchFragmentInHiltContainer<CommunityMembersFragment> {}
        onView(withId(R.id.community_members_root)).check(matches(isDisplayed()))
    }

    @Test
    fun test_recycler_view_members_is_visible() {
        launchFragmentInHiltContainer<CommunityMembersFragment> {}
        onView(withId(R.id.recycler_view_members)).check(matches(isDisplayed()))
    }
}