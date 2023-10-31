package com.huaguang.testandroid.dialog

sealed class DeleteDialogState(
    val target: String = "",
    val show: Boolean = false,
    open val onConfirm: () -> Unit = {},
) {
    object Hidden : DeleteDialogState()

    // 删除关键词
    data class DeleteKeyword(
        override val onConfirm: () -> Unit
    ) : DeleteDialogState(
        target = "关键词",
        show = true,
        onConfirm = onConfirm,
    )

    // 删除类属
    data class DeleteCategory(
        override val onConfirm: () -> Unit
    ) : DeleteDialogState(
        target = "类属",
        show = true,
        onConfirm = onConfirm,
    )

}

