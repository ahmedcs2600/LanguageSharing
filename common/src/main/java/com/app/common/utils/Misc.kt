package com.app.common.utils

import java.util.*
import kotlin.random.Random

object Misc {
    fun getRandomLong(): Long {
        return Random.nextLong()
    }

    fun getRandomInt(): Int {
        return Random.nextInt()
    }

    fun generateRandomString() = UUID.randomUUID().toString().substring(0, 15)
}