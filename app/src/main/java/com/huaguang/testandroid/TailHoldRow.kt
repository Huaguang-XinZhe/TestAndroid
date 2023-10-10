package com.huaguang.testandroid

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * 保持后一个 Composable 的 Row，可能将最后一个 composable 挤出布局的组件必须放在倒数第二个！
 */
@Composable
fun TailHoldRow(
    modifier: Modifier = Modifier,
    composables: List<@Composable () -> Unit>,
    middlePadding: Dp = 8.dp,
) {
    Layout(
        content = {
            composables.forEach { it() }
        },
        measurePolicy = { measurables, constraints ->
            // 测量最后一个组件
            val holdComponent = measurables.last().measure(constraints)

            // 初始化宽度和高度
            var width = holdComponent.width
            var height = holdComponent.height

            // 前边的组件的列表
            val frontComponents = mutableListOf<Placeable>()

            // 测量剩余的组件
            for (i in 0 until measurables.size - 1) {
                val frontComponent = measurables[i].measure(
                    constraints.copy(maxWidth = constraints.maxWidth - width)
                )
                frontComponents.add(frontComponent)

                width += frontComponent.width + middlePadding.roundToPx()
                height = maxOf(height, frontComponent.height)
            }

            layout(width, height) {
                // 布局最后一个组件
                holdComponent.placeRelative(
                    x = width - holdComponent.width,
                    y = (height - holdComponent.height) / 2
                )

                // 布局其他组件
                var xOffset = 0
                frontComponents.forEach { frontComponent ->
                    frontComponent.placeRelative(
                        x = xOffset,
                        y = (height - frontComponent.height) / 2
                    )
                    xOffset += frontComponent.width + middlePadding.roundToPx()
                }
            }
        },
        modifier = modifier
    )
}


@Preview(showBackground = true)
@Composable
fun Test() {
    TailHoldRow(
        composables = listOf(
            {
                Text("第二个", Modifier.background(Color.Green).padding(16.dp))
            },
            {
                Text(
                    text = "第一个51下, 本人思来想去, 寝第一个51下, 本人思来想去, 寝",
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.background(Color.Red).padding(16.dp)
                )
            },
            { Text("第三个", Modifier.background(Color.Blue).padding(16.dp)) }
        ),
        middlePadding = 8.dp
    )
}
