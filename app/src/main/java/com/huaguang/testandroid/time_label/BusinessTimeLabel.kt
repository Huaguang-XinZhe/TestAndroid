package com.huaguang.testandroid.time_label

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.huaguang.testandroid.TAG
import com.huaguang.testandroid.format
import com.huaguang.testandroid.record_block.EventType
import com.huaguang.testandroid.widget.TimeLabel
import java.time.LocalDateTime

/**
 * 时间标签组件（含业务逻辑）
 *
 * 1. 点击后选中，调整后固定；
 * 2. 不同的时间标签可以分别调整，互不影响；
 * 3. 时间标签一出现，自动选中（可选模式）；
 * 4. 没选中点击按钮无效；
 */
@Composable
fun BusinessTimeLabel(
    timeLabelState: TimeLabelState,
    onTimeSelected: (TimeLabelState) -> Unit
) {
    if (timeLabelState.isShow.value.not()) return

    val onSelected: () -> Unit = {
        timeLabelState.isSelected.value = true
        onTimeSelected(timeLabelState)
    }

    val dynamicTime = timeLabelState.dynamicTime.value
    val timeString = remember(dynamicTime) { dynamicTime.format() }

    Log.d(TAG, "BusinessTimeLabel: timeString = $timeString")
    TimeLabel(
        timeString = timeString,
        textSize = timeLabelState.textSize,
        isSelected = timeLabelState.isSelected.value,
        onSelected = onSelected
    )
}

@Composable
fun TimeAdjustButton(
    timeLabelState: TimeLabelState,
    onTimeUpdated: (TimeLabelState) -> Unit
) {
    Button(onClick = {
        if (timeLabelState.isSelected.value.not()) return@Button // 没选中，点击无效！
        timeLabelState.dynamicTime.value = timeLabelState.dynamicTime.value.plusMinutes(1)
        onTimeUpdated(timeLabelState)
    }) {
        Text("增加一分钟")
    }
}

@Preview(showBackground = true)
@Composable
fun SomeContainer() {
    val stateList = remember {
        listOf(
            TimeLabelState(
                eventType = EventType.MAIN,
                labelType = TimeLabelType.START,
                eventId = 1L,
                initialTime = LocalDateTime.now(),
            ),
            TimeLabelState(
                eventType = EventType.MAIN,
                labelType = TimeLabelType.END,
                eventId = 1L,
                initialTime = LocalDateTime.now().plusHours(1),
            )
        )
    }

    Column(modifier = Modifier.padding(10.dp)) {
        stateList.forEach { state ->
            BusinessTimeLabel(
                timeLabelState = state,
                onTimeSelected = { labelState ->
                    // 处理 TimeLabel 被选中的逻辑
                }
            )
            TimeAdjustButton(
                timeLabelState = state,
                onTimeUpdated = { labelState ->
                    // 时间更新后的逻辑，例如更新数据库
                    // 更新成功后，可以重置 TimeLabel 的选中状态
                    // todo 没有操作后 1.2 秒后再重置
//                    labelState.isSelected.value = false
                    // 并更新初始时间
                    labelState.initialTime = labelState.dynamicTime.value
                }
            )
        }
    }
}

