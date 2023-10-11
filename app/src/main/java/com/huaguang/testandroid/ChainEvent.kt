package com.huaguang.testandroid

import java.time.Duration
import java.time.LocalDateTime


data class ChainEvent(
    val startTime: LocalDateTime,
    val childEvents: List<InternalEvent>, // 不为 null，至少都要指定一个，也就是头部（主题事件）
    val endTime: LocalDateTime? = null,
    val category: String? = null,
    val remark: String? = null, // 这是所有内部事件（包括开头的主题事件）备注的集合，链事件切换为展示模式的时候，其内部事件不显示备注
    val tags: List<String>? = null, // 内部事件的标签汇聚而成
)

data class InternalEvent(
    val name: String,
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

