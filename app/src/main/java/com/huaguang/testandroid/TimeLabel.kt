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
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.time.LocalDateTime

@Composable
fun TimeLabel(
    modifier: Modifier = Modifier,
    time: LocalDateTime?,
    selectedState: MutableState<Boolean>,
    textSize: TextUnit = 16.sp,
    onClick: () -> Unit,
) {
    if (time == null) return

    val interactionSource = remember { MutableInteractionSource() }
    val shape = RoundedCornerShape(4.dp)
    val borderColor = if (selectedState.value) {
        MaterialTheme.colorScheme.primary
    } else Color.LightGray

    Box(
        modifier = modifier
            // 必须放在 clickable 前边，要不然涟漪效果的形状依然是方形。其次，只 clip 没用，border 和 background 也必须指定 shape，否则会显示异常
            .clip(shape = shape)
            .clickable(
                interactionSource = interactionSource,
                indication = rememberRipple(bounded = true), // 必须设为 true，为 false 的话水波纹的最大范围是一个以组件宽度为直径的圆形
                onClick = {
                    if (!selectedState.value) { // 没选中的话，点击才有效
                        selectedState.value = true
                        onClick()
                    }
                }
            )
            .border(0.6.dp, borderColor, shape = shape)
            .background(borderColor.copy(alpha = 0.1f), shape = shape)
            .padding(horizontal = 3.dp)
    ) {
        Text(
            text = time.format(),
            color = borderColor,
            fontSize = textSize
        )
    }

}
