package com.huaguang.testandroid

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.layout.Layout

@Composable
fun TailHoldRow(
    content: @Composable RowScope.() -> Unit
) {
    Layout(
        content = {
            Row { content() }
        },
        measurePolicy = { measurables, constraints ->
            // 首先测量后边的组件，以便知道剩余可用空间
            val holdComponent = measurables.last().measure(constraints)

            // 然后测量前边的组件，给定减去后边的组件宽度的约束
            val frontWidth = constraints.maxWidth - holdComponent.width
            val frontConstraints = constraints.copy(maxWidth = frontWidth)
            val frontComponent = measurables.first().measure(frontConstraints)

            // 计算总宽度和高度
            val width = frontComponent.width + holdComponent.width
            val height = maxOf(frontComponent.height, holdComponent.height)

            layout(width, height) {
                // 布局元素
                frontComponent.placeRelative(0, 0)
                holdComponent.placeRelative(
                    x = frontComponent.width,
                    y = (frontComponent.height - holdComponent.height) / 2 // y 这么写是为了居中
                )
            }
        }
    )
}
