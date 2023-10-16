package com.huaguang.testandroid.record_block

import java.time.Duration
import java.time.LocalDateTime


//data class ChainEvent(
//    val mainEvent: InternalEvent,
//    val childEvents: List<InternalEvent>, // 不为 null，至少都要指定一个，也就是头部（主题事件）
//)

data class InternalEvent(
    val id: Long = 0,
    val startTime: LocalDateTime,
    val name: String? = null,
    var endTime: LocalDateTime? = null,
    val duration: Duration? = null,
    val interval: Int = 0,
    val remark: String? = null,
    val label: String? = null,
    val type: EventType = EventType.ADD
)

// 伴随每个事件的标志
enum class EventType {
    MAIN,
    ADD,
    INSERT,
}

// 全局性的标志，依情境而变，不伴随每个事件
// 如果为 null，代表主题事件已经结束
enum class CurrentType {
    MAIN,
    ADD,
    INSERT
}
