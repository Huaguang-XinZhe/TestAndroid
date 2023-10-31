package com.huaguang.testandroid.cache

import java.time.LocalDateTime

data class TimeCache(
    var index: Int = 0,
    var mainStart: LocalDateTime? = null,
    var mainEnd: LocalDateTime? = null,
    var currentStart: LocalDateTime? = null,
    var currentEnd: LocalDateTime? = null
)
