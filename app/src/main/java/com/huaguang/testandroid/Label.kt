package com.huaguang.testandroid

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.time.LocalDateTime


@Composable
fun Label(
    modifier: Modifier = Modifier,
    type: LabelType
) {
    type.apply {

        val borderModifier = if (isDashBorder) {
            Modifier.dashBorder(borderColor)
        } else {
            Modifier.border(0.5.dp, borderColor, shape)
        }

        Box(
            modifier = modifier
                .then(borderModifier) // dashBorder 放在 clip 后边会被 clip 遮挡，放在前边不会？
                .clip(shape)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = rememberRipple(bounded = true),
                    onClick = onClick
                )
//            .dashBorder() // 放这里会被 clip 剪切（遮挡不全）
                .background(bgColor, shape = shape)
                .padding(horizontal = horizontalPadding, vertical = verticalPadding)
//            .dashBorder() // 放在这里会被 padding 挤到组件里边来
        ) {
            Text(
                text = displayText,
                color = textColor,
//            modifier = Modifier.align(Alignment.Center) // 加和不加都一样，是居中的
            )
        }
    }
}

/**
 * 这个具象标签能不能合并到上边呢？
 */
@Composable
fun TimeLabel(
    modifier: Modifier = Modifier,
    time: LocalDateTime,
    onSelected: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val shape = RoundedCornerShape(4.dp)
    val borderColor = MaterialTheme.colorScheme.primary

    Box(
        modifier = modifier
            // 必须放在 clickable 前边，要不然涟漪效果的形状依然是方形。其次，只 clip 没用，border 和 background 也必须指定 shape，否则会显示异常
            .clip(shape = shape)
            .clickable(
                interactionSource = interactionSource,
                indication = rememberRipple(bounded = true), // 必须设为 true，为 false 的话水波纹的最大范围是一个以组件宽度为直径的圆形
                onClick = onSelected
            )
            .border(0.5.dp, borderColor, shape = shape)
            .background(borderColor.copy(alpha = 0.1f), shape = shape)
            .padding(horizontal = 3.dp) // 可以根据需要调整这个值
    ) {
        Text(
            text = time.format(),
            color = MaterialTheme.colorScheme.primary,
            fontSize = 12.sp
        )
    }

}