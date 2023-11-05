package com.huaguang.testandroid.time_label

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.huaguang.testandroid.record_block.EventType
import java.time.LocalDateTime

/**
 * 时间标签的状态，包括部分事件状态（必需）
 *
 * @param eventId 事件 id
 * @param eventType 事件类型
 * @param textSize 文字大小
 * @param labelType 标签类型（开始、结束）
 * @param initialTime 初始时间
 * @param dynamicTime 动态时间（可变）
 * @param isShow 是否显示
 * @param isSelected 是否选中
 */
data class TimeLabelState(
    val eventId: Long,
    val eventType: EventType,
    val textSize: TextUnit = 12.sp,
    val labelType: TimeLabelType,
    var initialTime: LocalDateTime,
    val dynamicTime: MutableState<LocalDateTime> = mutableStateOf(initialTime),
    val isShow: MutableState<Boolean> = mutableStateOf(true), // 默认显示
    val isSelected: MutableState<Boolean> = mutableStateOf(true),
)

enum class TimeLabelType {
    START, END
}
