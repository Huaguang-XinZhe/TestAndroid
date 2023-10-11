package com.huaguang.testandroid

import androidx.annotation.DrawableRes
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


sealed class RecordItemStyle(
    val iconSize: Dp = 15.dp,
    val iconTopPadding: Dp = 1.dp,
    val nameSize: TextUnit = 17.sp,
    @DrawableRes val iconRes: Int = R.drawable.add,
    val isTag: Boolean = true
) {
    object Main : RecordItemStyle(
        iconSize = 18.dp,
        nameSize = 20.sp,
        iconRes = R.drawable.main,
        isTag = false
    )

    object Add : RecordItemStyle()

    object Insert : RecordItemStyle(
        iconTopPadding = 0.dp,
        iconRes = R.drawable.insert,
    )
}
