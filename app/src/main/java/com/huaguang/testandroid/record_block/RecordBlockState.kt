package com.huaguang.testandroid.record_block

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf


/**
 * 记录过程中的状态变化，可以单独改变里边的状态，非常灵活。
 * 这里不好使用密封类的形式定义几个特定的状态，情况比较复杂，不太好区分。这种情况还是灵活一点的好。
 */
data class RecordBlockState(
    val supplementButtonShow: MutableState<Boolean> = mutableStateOf(false),
    val intervalButtonShow: MutableState<Boolean> = mutableStateOf(false),
    val startTimeShow: MutableState<Boolean> = mutableStateOf(false),
    val endTimeShow: MutableState<Boolean> = mutableStateOf(false),
    val timeLabelSelected: MutableState<Boolean> = mutableStateOf(false), // 必须为 false，否则一开始就是调节模式
    val mainStartLabelSelected: MutableState<Boolean> = mutableStateOf(false),
)