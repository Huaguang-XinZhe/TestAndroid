package com.huaguang.testandroid.time_label

import androidx.compose.runtime.MutableState
import java.time.LocalDateTime

// 定义 TimeLabelState 以及 TimeLabelType
data class TimeLabelState(
    val type: TimeLabelType,
    val eventId: Long,
    var initialTime: LocalDateTime,
    val isSelected: MutableState<Boolean>,
    val dynamicTime: MutableState<LocalDateTime>
)

enum class TimeLabelType {
    START, END
}
