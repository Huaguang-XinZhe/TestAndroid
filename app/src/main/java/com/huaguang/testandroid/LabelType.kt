package com.huaguang.testandroid

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.huaguang.testandroid.ui.theme.DeepGreen
import com.huaguang.testandroid.ui.theme.Primary

sealed class LabelType(
    val shape: Shape,
    val borderColor: Color,
    val bgColor: Color,
    val textColor: Color,
    val horizontalPadding: Dp,
    val verticalPadding: Dp,
    val displayText: String,
    val isDashBorder: Boolean = false,
) {
    // 匿名内部类不能有参数，这里必须用 class 或 data class
    object Add : LabelType(
        shape = CircleShape,
        borderColor = Primary, // TODO: 这里写死了，该如何应用主题呢？
        bgColor = Primary.copy(alpha = 0.1f),
        textColor = Primary,
        horizontalPadding = 15.dp,
        verticalPadding = 1.dp,
        displayText = "+",
        isDashBorder = true,
    )

    data class Category(
        val name: String,
    ) : LabelType(
        shape = CircleShape,
        borderColor = Color.Transparent,
        bgColor = DeepGreen,
        textColor = Color.White,
        horizontalPadding = 6.dp,
        verticalPadding = 3.dp,
        displayText = "@$name",
    )

    data class Tag(
        val name: String,
    ) : LabelType(
        shape = RoundedCornerShape(4.dp),
        borderColor = Color.DarkGray,
        bgColor = Color.DarkGray.copy(alpha = 0.05f),
        textColor = Color.DarkGray,
        horizontalPadding = 3.dp,
        verticalPadding = 0.dp,
        displayText = "#$name",
    )
}
