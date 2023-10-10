package com.huaguang.testandroid

import java.time.Duration
import java.time.LocalDateTime


data class ChainEvent(
    val startTime: LocalDateTime,
    val childEvents: List<InternalEvent>,
    val endTime: LocalDateTime? = null,
    val category: String? = null,
    val remarks: List<String>? = null, // 这是所有内部事件备注的集合，链事件展示的时候，其内部事件不显示备注
    val tags: List<String>? = null, // 内部事件的标签汇聚而成
)

data class InternalEvent(
    val name: String,
    val duration: Duration? = null,
    val remark: String? = null,
    val tag: String? = null,
    val type: EventType = EventType.ADD
)

enum class EventType {
    ADD,
    INSERT,
}

