package com.huaguang.testandroid

/**
 * 这 toggleItem 从展示模式切换至记录模式时，事项的呈现状态（模式）
 * 所有事项已完成
 */
// TODO: 这一部分以后可能要删掉，但先留着，做个参考
sealed class RecordBlockDisplayMode (
    val startTimeShow: Boolean = false,
    val endTimeShow: Boolean = false,
    val intervalButtonShow: Boolean = true,
) {
    // 已经完成
    object Default : RecordBlockDisplayMode()

    object Expand : RecordBlockDisplayMode(
        intervalButtonShow = false,
        startTimeShow = true,
        endTimeShow = true,
    )

    object Compress : RecordBlockDisplayMode(
        intervalButtonShow = false,
    )

}