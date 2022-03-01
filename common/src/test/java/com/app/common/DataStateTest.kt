package com.app.common

import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class DataStateTest {

    @Test
    fun `test invoke DataState success() returns DataState Success and its data`() {
        //Given
        val givenData = 10

        //Invoke
        val data = DataState.success(givenData)

        //Then
        MatcherAssert.assertThat(data.data,CoreMatchers.instanceOf(Int::class.java))
        MatcherAssert.assertThat(data.data,CoreMatchers.`is`(givenData))
    }

    @Test
    fun `test invoke DataState error() returns DataState Error and error message`() {
        //Given
        val givenData = "SomeThing went wrong"

        //Invoke
        val data = DataState.error<Int>(givenData)

        //Then
        MatcherAssert.assertThat(data.message,CoreMatchers.`is`(givenData))
    }
}