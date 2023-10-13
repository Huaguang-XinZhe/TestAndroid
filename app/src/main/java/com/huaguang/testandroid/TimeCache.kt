package com.huaguang.testandroid

import java.time.LocalDateTime

data class TimeCache(
    var mainStart: LocalDateTime? = null,
    var mainEnd: LocalDateTime? = null,
    var currentStart: LocalDateTime? = null,
    var currentEnd: LocalDateTime? = null
)
