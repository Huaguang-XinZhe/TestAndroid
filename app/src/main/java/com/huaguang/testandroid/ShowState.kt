package com.huaguang.testandroid

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

data class ShowState(
    val regulatorBarShow: MutableState<Boolean> = mutableStateOf(false),
    val buttonsBarShow: MutableState<Boolean> = mutableStateOf(true),
    val recordBlockShow: MutableState<Boolean> = mutableStateOf(true),
    val intervalButtonShow: MutableState<Boolean> = mutableStateOf(false),
    val internalStartTimeShow: MutableState<Boolean> = mutableStateOf(false),
    val internalEndTimeShow: MutableState<Boolean> = mutableStateOf(false),
    val supplementButtonShow: MutableState<Boolean> = mutableStateOf(true),
)


sealed class InternalItemState (
    val startTimeShow: Boolean = false,
    val endTimeShow: Boolean = false,
    val supplementButtonShow: Boolean = false,
    val intervalButtonShow: Boolean = true,
) {
    // 已经完成
    object Default : InternalItemState()

    object Expand : InternalItemState(
        intervalButtonShow = false,
        startTimeShow = true,
        endTimeShow = true,
    )

    // 还在进行……
    object Timing : InternalItemState(
        intervalButtonShow = false,
        supplementButtonShow = true
    )

    object Start : InternalItemState(
        intervalButtonShow = false,
        startTimeShow = true,
    )

    object End : InternalItemState(
        intervalButtonShow = false,
        endTimeShow = true,
    )

    // 通用状态！！！
    object Compress : InternalItemState(
        intervalButtonShow = false,
    )

}


