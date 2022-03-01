package com.app.common.mapper

interface Mapper<IN,OUT> {
    fun mapTo(input : IN) : OUT
}