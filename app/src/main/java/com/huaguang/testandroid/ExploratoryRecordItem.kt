package com.huaguang.testandroid

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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.time.Duration
import java.time.LocalDateTime

@Composable
fun ExploratoryRecordItem(
    chainEvent: ChainEvent,
) {
    chainEvent.apply {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
        ) {
            TimeLabel(
                time = startTime,
            ) {  }

            VerticalLine()

            childEvents.forEach { internalEvent ->
                InternalColumn(event = internalEvent)
                VerticalLine()
            }

            if (endTime != null) {
                TimeLabel(
                    time = endTime,
                ) {  }
            }
        }
    }
}

@Composable
fun InternalColumn(
    event: InternalEvent,
) {
    event.apply {

        val style = when (event.type) {
            EventType.MAIN -> RecordItemStyle.Main
            EventType.ADD -> RecordItemStyle.Add
            EventType.INSERT -> RecordItemStyle.Insert
        }
        
        // Card 的内 padding 没有效果，内 padding 也是外 padding
        OutlinedCard {
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
    }
}

@Composable
fun NameRow(
    modifier: Modifier = Modifier,
    event: InternalEvent,
    style: RecordItemStyle
) {
    style.apply {
        TailHoldRow(
            composables = listOf(
                {
                    Icon(
                        painter = painterResource(id = iconRes),
                        contentDescription = null,
                        modifier = Modifier.size(iconSize).padding(top = iconTopPadding),
                        tint = MaterialTheme.colorScheme.primary
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


@Preview(showBackground = true)
@Composable
fun ExploratoryRecordItemTest() {

    val internalEvent = InternalEvent(
        name = "贵州省毕节市赫章县水塘堡彝族苗族乡",
        remark = "在,说, 随机一段废话对我的意义x不仅仅是一个重大的",
        type = EventType.MAIN,
        label = "当前核心"
    )

    // TODO: 有没有工具能够根据数据结构自动生成假数据（创建对象的代码）？
    val internalEvent1 = InternalEvent(
        name = "敏而好学，不耻下问",
        duration = Duration.ofMinutes(183),
        remark = "河北工商大学",
        label = "探索"
    )

    val internalEvent2 = InternalEvent(
        name = "森森的33.190.80.243",
        type = EventType.INSERT,
        duration = Duration.ofMinutes(12)
    )

    val internalEvent3 = InternalEvent(
        name = "广西壮族自治区南宁市隆安县城厢镇",
        duration = Duration.ofMinutes(23),
        remark = "了解清楚随机一段废话到底是一种怎么样的存在,说, 随机一段废话对我的意义x不仅仅是一个重大的事件，还可能会改变我的人生.",
        label = "当前核心"
    )

    val internalEvent4 = InternalEvent(
        name = "a14f@sgf11zp.com",
        duration = Duration.ofMinutes(63),
        label = "核心"
    )

    val chainEvent = ChainEvent(
        startTime = LocalDateTime.now(),
        childEvents = listOf(internalEvent, internalEvent1, internalEvent2, internalEvent3, internalEvent4),
        endTime = LocalDateTime.now(),
        category = "时光流开发",
    )

    ExploratoryRecordItem(chainEvent = chainEvent)

}