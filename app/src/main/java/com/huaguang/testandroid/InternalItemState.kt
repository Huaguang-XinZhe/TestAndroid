package com.huaguang.testandroid

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


