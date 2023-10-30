package com.huaguang.testandroid.dialog

sealed class DialogState(
    val title: String = "",
    val label: String = "",
    val show: Boolean = false,
    open val initialValue: String = "", // 对话框弹出时输入框中显示的初始值
    open val onConfirm: (String) -> Unit = {} // 点击确认按钮时的回调函数
) {
    object Hidden : DialogState()

    // 新增类属
    data class AddCategory(
        override val onConfirm: (String) -> Unit
    ) : DialogState(
        title = "新增类属",
        label = "Category Name",
        show = true,
        onConfirm = onConfirm,
    )


    // 新增关键词
    data class AddKeyword(
        override val onConfirm: (String) -> Unit
    ) : DialogState(
        title = "新增关键词",
        label = "Keyword Name",
        show = true,
        onConfirm = onConfirm,
    )


    // 更新关键词
    data class UpdateKeyword(
        override val initialValue: String,
        override val onConfirm: (String) -> Unit
    ) : DialogState(
        title = "更新关键词",
        label = "Keyword Name",
        show = true,
        initialValue = initialValue,
        onConfirm = onConfirm
    )
}


