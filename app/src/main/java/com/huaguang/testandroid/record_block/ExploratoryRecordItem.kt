package com.huaguang.testandroid.record_block

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.huaguang.testandroid.RecordPageViewModel
import com.huaguang.testandroid.TimeState
import com.huaguang.testandroid.format
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
            textSize = 16.sp
        )

        events.forEachIndexed { index, internalEvent ->
            InternalEventItem(
                event = internalEvent,
            )
        }

        TimeLabelWithLine(
            event = events.first(),
            isStart = false,
            textSize = 16.sp
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
        TimeLabelWithLine(event, isStart = false)
    }
}

@Composable
fun TimeLabelWithLine(
    event: InternalEvent,
    isStart: Boolean = true,
    textSize: TextUnit = 12.sp,
) {
    TimeLabel(
        timeState = TimeState(
            eventId = event.id,
            isStart = isStart,
            eventType = event.type,
            initialTime = if (isStart) event.startTime else event.endTime,
        ),
        textSize = textSize
    )

    if (!isStart) return
    VerticalLine()
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

    ExploratoryRecordBlock(events = events)

}