package com.huaguang.testandroid

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

@Composable
fun Label(
    modifier: Modifier = Modifier,
    type: LabelType,
    onClick: () -> Unit
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
