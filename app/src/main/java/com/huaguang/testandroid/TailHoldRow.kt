package com.huaguang.testandroid

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedCard
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
    middleSpacing: Dp = 8.dp,
) {
    Layout(
        content = {
            composables.forEach { it() }
        },
        measurePolicy = { measurables, constraints ->
            val middleSpacingPx = middleSpacing.roundToPx()

            // 测量最后一个组件
            val holdComponent = measurables.last().measure(constraints)

            // 初始化宽度和高度
            var width = holdComponent.width
            var height = holdComponent.height

            // 前边的组件的列表
            val frontComponents = mutableListOf<Placeable>()

            // 测量剩余的组件
            // 主题，这个 until 不包括最后一个！！！
            for (i in 0 until measurables.size - 1) {
                val frontComponent = measurables[i].measure(
                    constraints.copy(
                        // 这一句是核心，单个组件的最大宽度必须要减去 middleSpacingPx，否则两侧间距在中间组件名称过长时会被压缩！！！
                        maxWidth = constraints.maxWidth - width - middleSpacingPx
                    )
                )
                frontComponents.add(frontComponent)

                width += frontComponent.width + middleSpacingPx
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
                    xOffset += frontComponent.width + middleSpacingPx
                }
            }
        },
        modifier = modifier
    )
}


@Preview(showBackground = true)
@Composable
fun Test() {
    OutlinedCard(
        modifier = Modifier.fillMaxWidth().padding(10.dp)
    ) {
        TailHoldRow(
            composables = listOf(
                {
                    Text("第二个", Modifier.background(Color.Green).padding(16.dp))
                },
                {
                    Text(
                        text = "第一个51下, 本人思来想去, 寝第一个51下, 本人思来想去, 寝随机一段废话似乎是一种巧合，但如果我们从一个更大的角度看待问题，这似乎是一种不可避免的事实. 这是不可避免的. 本人也是经过了深思熟虑,在每个日日夜夜思考这个问题. 带着这些问题, 我们来审视一下随机一段废话. 了解清楚随机一段废话到底是一种怎么样的存在, 是解决一切问题的关键.问题的关键究竟为何? 我们都知道, 只要有意义, 那么就必须慎重考虑.问题的关键究竟为何? 随机一段废话似乎是一种巧合，",
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.background(Color.Red).padding(16.dp)
                    )
                },
                { Text("第三个", Modifier.background(Color.Blue).padding(16.dp)) }
            ),
            modifier = Modifier.padding(horizontal = 10.dp)
        )
    }
}
