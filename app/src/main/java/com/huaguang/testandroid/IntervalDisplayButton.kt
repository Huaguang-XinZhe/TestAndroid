package com.huaguang.testandroid

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun IntervalDisplayButton(
    modifier: Modifier = Modifier,
    interval: Int, // 几分钟（间隔）
    show: Boolean = true,
    onClick: () -> Unit
) {
    if (!show) return

    val shape = RoundedCornerShape(6.dp)

    Box(
        modifier = modifier.clip(shape)
            .background(
                color = Color.LightGray.copy(alpha = 0.3f),
                shape = shape
            )
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(bounded = false),
                onClick = onClick
            )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.padding(horizontal = 5.dp, vertical = 1.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.expand_vertical),
                contentDescription = null,
                tint = Color.DarkGray,
                modifier = Modifier.size(12.dp)
            )

            Spacer(modifier = Modifier.padding(start = 5.dp))

            Text(
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(
                        fontSize = 13.sp,
                        fontWeight = FontWeight.ExtraBold,
                    )
                    ) {
                        append(interval.toString())
                    }
                    withStyle(style = SpanStyle(
                        fontSize = 12.sp,
                    )
                    ) {
                        append(" 分钟后")
                    }
                },
                color = Color.DarkGray
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun IntervalDisplayButtonTest() {

    IntervalDisplayButton(
        interval = 8,
        modifier = Modifier.padding(10.dp)
    ) {

    }

}