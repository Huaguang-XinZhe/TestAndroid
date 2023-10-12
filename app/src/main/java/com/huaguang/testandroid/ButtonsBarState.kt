package com.huaguang.testandroid

sealed class ButtonsBarState(
    val barShow: Boolean = true,
    val mainStartButtonShow: Boolean = true,
    val addButtonText: String? = null, // 加上可空，就可以将一个状态当作两个状态来用，既能控制文本变化，又能控制视图显示
    val insertButtonText: String? = null,
) {
    object Default : ButtonsBarState()

    object AllDisplay : ButtonsBarState(
        mainStartButtonShow = false,
        addButtonText = "新增",
        insertButtonText = "插入"
    )

    object AddOverDisplay : ButtonsBarState(
        mainStartButtonShow = false,
        addButtonText = "新增结束",
    )

    object InsertOverDisplay : ButtonsBarState(
        mainStartButtonShow = false,
        insertButtonText = "插入结束",
    )
}
