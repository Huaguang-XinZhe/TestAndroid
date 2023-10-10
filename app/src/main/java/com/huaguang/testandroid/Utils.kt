package com.huaguang.testandroid

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import java.time.Duration
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun LocalDateTime.format(): String {
    val formatter = DateTimeFormatter.ofPattern("HH:mm")
    return this.format(formatter)
}

fun Duration.format(englishUnit: Boolean = true): String {
    val hUnit: String
    val mUnit: String
    val hours = this.toHours()
    val remainingMinutes = this.minusHours(hours).toMinutes()

    if (englishUnit) {
        hUnit = "h"
        mUnit = "m"
    } else {
        hUnit = "小时"
        mUnit = "分钟"
    }

    return when {
        hours == 0L && remainingMinutes == 0L -> ""
        hours == 0L -> "$remainingMinutes$mUnit"
        remainingMinutes == 0L -> "$hours$hUnit"
        else -> "$hours$hUnit$remainingMinutes$mUnit"
    }
}

fun Modifier.dashBorder(
    borderColor: Color,
    radius: Float = 35f,
): Modifier {
    return this.drawWithContent { // 它允许我们在现有的绘制内容之上添加额外的绘制逻辑。
        drawContent() // 这个函数调用确保原有的内容被绘制，然后我们在其上添加我们的虚线边框。
        drawRoundRect(
            color = borderColor,
            style = Stroke(
                width = 3f,
                // phase 定义了虚线模式的相位，或者说是虚线模式的起始点。我们设置它为 0f 来从数组的开始处开始虚线模式。
                pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f),
                cap = StrokeCap.Round
            ),
            cornerRadius = CornerRadius(radius, radius) // 30~35 就比较贴合 CircleShape 了
        )
    }
}
