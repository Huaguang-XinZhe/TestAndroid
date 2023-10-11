package com.huaguang.testandroid

import java.time.Duration
import java.time.LocalDateTime


data class ChainEvent(
    val mainEvent: InternalEvent,
    val childEvents: List<InternalEvent>, // 不为 null，至少都要指定一个，也就是头部（主题事件）
)

data class InternalEvent(
    val name: String,
    val startTime: LocalDateTime,
    val endTime: LocalDateTime? = null,
    val duration: Duration? = null,
    val remark: String? = null,
    val label: String? = null,
    val type: EventType = EventType.ADD
)

enum class EventType {
    MAIN,
    ADD,
    INSERT,
}

