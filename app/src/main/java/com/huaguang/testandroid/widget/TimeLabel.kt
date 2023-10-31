package com.huaguang.testandroid.widget

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * 纯粹的、最底层的 TimeLabel，只能显示时间文本和表示选中状态
 */
@Composable
fun TimeLabel(
    modifier: Modifier = Modifier,
    timeString: String,
    textSize: TextUnit = 12.sp,
    isSelected: Boolean = false,
    onSelected: () -> Unit
) {
    val shape = RoundedCornerShape(4.dp)
    val borderColor = if (isSelected) MaterialTheme.colorScheme.primary else Color.LightGray

    Box(
        modifier = modifier
            .clip(shape = shape)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(bounded = true),
                onClick = onSelected
            )
            .border(0.6.dp, borderColor, shape = shape)
            .background(borderColor.copy(alpha = 0.1f), shape = shape)
            .padding(horizontal = 3.dp)
    ) {
        Text(
            text = timeString,
            color = borderColor,
            fontSize = textSize
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TimeLabelTest() {
    Box(
        modifier = Modifier.padding(10.dp)
    ) {
        TimeLabel(timeString = "12:30") {

        }
    }
}