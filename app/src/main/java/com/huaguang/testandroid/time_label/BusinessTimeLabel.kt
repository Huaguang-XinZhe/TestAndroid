package com.huaguang.testandroid.time_label

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.huaguang.testandroid.format
import com.huaguang.testandroid.widget.TimeLabel
import java.time.LocalDateTime

/**
 * 本页代码非常适合学习、测试、留底，存档，不要动它了！！！
 */
@Composable
fun BusinessTimeLabel(
    timeLabelState: TimeLabelState,
    onTimeSelected: (TimeLabelState) -> Unit
) {
    val dynamicTime = timeLabelState.dynamicTime.value
    val timeString = remember(dynamicTime) { dynamicTime.format() }

    val onSelected: () -> Unit = {
        timeLabelState.isSelected.value = true
        onTimeSelected(timeLabelState)
    }

    TimeLabel(
        timeString = timeString,
        textSize = 16.sp,
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
                type = TimeLabelType.START,
                eventId = 1L,
                initialTime = LocalDateTime.now(),
                isSelected = mutableStateOf(false),
                dynamicTime = mutableStateOf(LocalDateTime.now())
            ),
            TimeLabelState(
                type = TimeLabelType.END,
                eventId = 1L,
                initialTime = LocalDateTime.now().plusHours(1),
                isSelected = mutableStateOf(false),
                dynamicTime = mutableStateOf(LocalDateTime.now().plusHours(1))
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

