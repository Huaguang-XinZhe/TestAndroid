package com.huaguang.testandroid.record_block

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.huaguang.testandroid.RecordPageViewModel
import com.huaguang.testandroid.TAG
import com.huaguang.testandroid.format
import com.huaguang.testandroid.time_label.BusinessTimeLabel
import com.huaguang.testandroid.time_label.TimeLabelState
import com.huaguang.testandroid.time_label.TimeLabelType
import com.huaguang.testandroid.widget.Label
import com.huaguang.testandroid.widget.LabelType
import com.huaguang.testandroid.widget.LongPressOutlinedIconButton
import com.huaguang.testandroid.widget.TailHoldRow
import java.time.Duration
import java.time.LocalDateTime

@Composable
fun ExploratoryRecordBlock(
    modifier: Modifier = Modifier,
    events: List<InternalEvent>
) {
    if (events.isEmpty()) return

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .padding(10.dp)
            .fillMaxWidth()
    ) {
        val mainEvent = events.first()

        TimeLabelWithLine(
            event = mainEvent,
        )

        events.forEach { internalEvent ->
            InternalEventItem(
                event = internalEvent,
            )
        }

        TimeLabelWithLine(
            event = events.first(),
            type = TimeLabelType.END,
        )
    }
}


@Composable
fun IntervalButtonWithLine(interval: Int) {
    if (interval < 1) return

    IntervalDisplayButton(interval = interval) {

    }

    VerticalLine()
}

@Composable
fun InternalEventItem(
    event: InternalEvent, // 伴随每个事件的数据
) {
    val style = when (event.type) {
        EventType.MAIN -> RecordItemStyle.Main
        EventType.ADD -> RecordItemStyle.Add
        EventType.INSERT -> RecordItemStyle.Insert
    }
    val isSub = event.type != EventType.MAIN

    if (isSub) { // 主题事件的开始和结束时间提到外边去了
        TimeLabelWithLine(event)
    }

    OutlinedCardItemWithLine(
        event = event,
        style = style
    )

    if (isSub) {
        TimeLabelWithLine(event, TimeLabelType.END)
    }
}

@Composable
fun TimeLabelWithLine(
    event: InternalEvent,
    type: TimeLabelType = TimeLabelType.START, // 用于区分开始和结束时间
    viewModel: RecordPageViewModel = viewModel()
) {
    val initialTime = if (type == TimeLabelType.START) event.startTime else event.endTime ?: return
    val textSize = if (event.type == EventType.MAIN) 16.sp else 12.sp

    // 使用 remember 来保留 TimeLabelState 实例
    val timeLabelState = remember(event.id, type) { // 用于标记唯一的 TimeLabelState（只要是同一个事件，同一种时间类型，就是同一个状态）
        TimeLabelState( // 这里边有三个状态，如果不记住，那么重组的时候就会重置
            eventId = event.id,
            eventType = event.type,
            textSize = textSize,
            labelType = type,
            initialTime = initialTime,
        )
    }

    BusinessTimeLabel(
        timeLabelState = timeLabelState,
        onTimeSelected = {
            // 在这里赋值才标准，如果用创建的 state 直接赋值，那在创建之外的选中场景就无法获取到 state 了
            viewModel.currentTimeLabelState = it
            Log.d(TAG, "TimeLabelWithLine: onTimeSelected 执行！")
            viewModel.toggleBar() // 只要一选中，就切换按钮栏的显示状态（会触发整个页面重组，进而触发当前组件重组）
        }
    )

    // 如果是主题事件的结束时间，就不要显示下边的竖线了
    if (event.type == EventType.MAIN && type == TimeLabelType.END) return
    if (timeLabelState.isShow.value) { // 伴随每个时间的显示状态
        VerticalLine()
    }
}

@Composable
fun OutlinedCardItemWithLine(
    event: InternalEvent,
    style: RecordItemStyle = RecordItemStyle.Main,
) {
    event.apply {
        // Card 的内 padding 没有效果，内 padding 也是外 padding
        OutlinedCard(
            colors = CardDefaults.outlinedCardColors(
                containerColor = style.backgroundColor
            )
        ) {
            val nameBottomPadding = if (remark == null && label == null) 10.dp else 2.dp
            val remarkBottomPadding = if (label == null) 10.dp else 0.dp

            NameRow(
                event = event,
                style = style,
                modifier = Modifier.padding(10.dp, 10.dp, 10.dp, nameBottomPadding)
            )

            InternalRemark(
                text = remark,
                modifier = Modifier.padding(30.dp, 0.dp, 10.dp, remarkBottomPadding)
            )

            SingleLabel(
                name = label,
                isTag = style.isTag,
                modifier = Modifier.padding(30.dp, 5.dp, 0.dp, 10.dp),
            ) {
                // TODO: 标签点击，可能是类属，也可能是 tag
            }
        }

        if (name == null || (type != EventType.MAIN && endTime == null)) return

        VerticalLine()
    }

}

@Composable
fun NameRow(
    modifier: Modifier = Modifier,
    event: InternalEvent,
    style: RecordItemStyle
) {
    if (event.name == null) return

    style.apply {
        TailHoldRow(
            composables = listOf(
                {
                    Icon(
                        painter = painterResource(id = iconRes),
                        contentDescription = null,
                        modifier = Modifier
                            .size(iconSize)
                            .padding(top = iconTopPadding),
                        tint = iconColor
                    )
                },
                {
                    Text(
                        text = event.name,
                        fontSize = nameSize,
                        fontWeight = FontWeight.SemiBold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                {
                    if (event.duration != null) {
                        Text(
                            text = event.duration.format(),
                            fontStyle = FontStyle.Italic,
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = nameSize,
                        )
                    } else {
                        Text(
                            text = "...",
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Bold,
                        )
                    }
                }
            ),
            modifier = modifier
        )
    }
}


@Composable
fun InternalRemark(
    modifier: Modifier = Modifier,
    text: String?
) {
    if (text == null) return

    Card(
        modifier = modifier,
        shape = RoundedCornerShape(5.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.LightGray.copy(alpha = 0.3f)
        )
    ) {
        Text(
            text = text,
            fontSize = 12.sp,
            color = Color.DarkGray,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(horizontal = 5.dp, vertical = 2.dp)
        )
    }
}

@Composable
fun SingleLabel(
    modifier: Modifier = Modifier,
    name: String?,
    isTag: Boolean,
    onClick: () -> Unit,
) {
    if (name == null) return

    val type = if (isTag) LabelType.Tag(name) else LabelType.Category(name)

    Label(
        type = type,
        onClick = onClick,
        modifier = modifier
    )
    
}

@Composable
fun VerticalLine() {
    Spacer(
        modifier = Modifier
            .width(1.dp)
            .height(10.dp)
            .background(color = Color.LightGray)
    )
}

/**
 * 用于补充记录的按钮，可支持点击和长按两种操作
 */
@Composable
fun SupplementButton(viewModel: RecordPageViewModel = viewModel()) {
    LongPressOutlinedIconButton(
        onClick = {
            viewModel.onSButtonClick()
        },
        onLongClick = {
            viewModel.onSButtonLongClick()
        }
    )

}

@Preview(showBackground = true)
@Composable
fun ExploratoryRecordItemTest() {

    val internalEvent = InternalEvent(
        name = "贵州省毕节市赫章县水塘堡彝族苗族乡",
        remark = "在,说, 随机一段废话对我的意义x不仅仅是一个重大的",
        type = EventType.MAIN,
        label = "当前核心",
        startTime = LocalDateTime.now(),
        endTime = LocalDateTime.now(),
        duration = Duration.ofMinutes(30),
        interval = 12
    )

    // TODO: 有没有工具能够根据数据结构自动生成假数据（创建对象的代码）？
    val internalEvent1 = InternalEvent(
        name = "敏而好学，不耻下问",
        remark = "河北工商大学",
        label = "探索",
        startTime = LocalDateTime.now(),
//        endTime = LocalDateTime.now(),
//        duration = Duration.ofMinutes(183),
        interval = 29
    )

    val internalEvent2 = InternalEvent(
        name = "森森的33.190.80.243",
        type = EventType.INSERT,
        duration = Duration.ofMinutes(12),
        startTime = LocalDateTime.now(),
        endTime = LocalDateTime.now(),
        interval = 77
    )

    val internalEvent3 = InternalEvent(
        name = "广西壮族自治区南宁市隆安县城厢镇",
        duration = Duration.ofMinutes(23),
        remark = "了解清楚随机一段废话到底是一种怎么样的存在,说, 随机一段废话对我的意义x不仅仅是一个重大的事件，还可能会改变我的人生.",
        label = "当前核心",
        startTime = LocalDateTime.now(),
        endTime = LocalDateTime.now()
    )

    val internalEvent4 = InternalEvent(
        name = "a14f@sgf11zp.com",
        duration = Duration.ofMinutes(63),
        label = "核心",
        startTime = LocalDateTime.now(),
        endTime = LocalDateTime.now()
    )

    val events = listOf(internalEvent, internalEvent1, )

//    ExploratoryRecordBlock(events = events)

    TimeLabelWithLine(event = internalEvent)
}