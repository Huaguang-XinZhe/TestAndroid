package com.huaguang.testandroid

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.huaguang.testandroid.record_block.EventType
import java.time.LocalDateTime

data class TimeState(
    var eventId: Long = 0,
    var isStart: Boolean = true,
    var eventType: EventType = EventType.MAIN,
    var initialTime: LocalDateTime? = null,
    val selected: MutableState<Boolean> = mutableStateOf(false),
    val show: MutableState<Boolean> = mutableStateOf(false),
)
