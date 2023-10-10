package com.huaguang.testandroid

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
                .padding(5.dp)
                .fillMaxWidth()
        ) {
            TimeLabel(
                time = startTime,
                modifier = Modifier.padding(vertical = 5.dp)
            ) {  }

            childEvents.forEach { internalEvent ->
                InternalColumn(event = internalEvent)
            }

            if (endTime != null) {
                TimeLabel(
                    time = endTime,
                    modifier = Modifier.padding(vertical = 5.dp)
                ) {  }
            }
        }
    }
}

@Composable
fun InternalColumn(
    event: InternalEvent,
) {
    OutlinedCard(
        modifier = Modifier.padding(5.dp) // Card 的内 padding 没有效果，内 padding 也是外 padding
    ) {
        val nameBottomPadding = if (event.tag == null) 10.dp else 2.dp

        NameRow(
            event = event,
            modifier = Modifier.padding(10.dp, 10.dp, 10.dp, nameBottomPadding)
        )

        InternalRemark(
            text = event.remark,
            modifier = Modifier.padding(horizontal = 30.dp)
        )

        SingleTag(
            name = event.tag,
            modifier = Modifier.padding(30.dp, 5.dp, 0.dp, 10.dp)
        )
    }
}

@Composable
fun NameRow(
    modifier: Modifier = Modifier,
    event: InternalEvent
) {
    TailHoldRow(
        composables = listOf(
            {
                val resId = when (event.type) {
                    EventType.ADD -> R.drawable.add
                    EventType.INSERT -> R.drawable.arrow_right
                }

                Icon(
                    painter = painterResource(id = resId),
                    contentDescription = null,
                    modifier = Modifier.size(15.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
            },
            {
                Text(
                    text = event.name,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            },
            {
                if (event.duration != null) {
                    Text(
                        text = event.duration.format(),
                        fontStyle = FontStyle.Italic
                    )
                } else {
                    Text(text = "...")
                }
            }
        ),
        modifier = modifier
    )
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
fun SingleTag(
    modifier: Modifier = Modifier, 
    name: String?,
) {
    if (name == null) return

    Label(
        type = LabelType.Tag(
            name = name,
            onClick = {
                // TODO:  
            }
        ),
        modifier = modifier
    )
    
}


@Preview(showBackground = true)
@Composable
fun ExploratoryRecordItemTest() {

    // TODO: 有没有工具能够根据数据结构自动生成假数据（创建对象的代码）？
    val internalEvent1 = InternalEvent(
        name = "敏而好学，不耻下问",
        duration = Duration.ofMinutes(183),
        remark = "河北工商大学",
        tag = "探索"
    )

    val internalEvent2 = InternalEvent(
        name = "33.190.80.243",
        type = EventType.INSERT,
//        duration = Duration.ofMinutes(183),
//        remark = "河北工商大学",
//        tag = "探索"
    )

    val internalEvent3 = InternalEvent(
        name = "广西壮族自治区南宁市隆安县城厢镇",
        duration = Duration.ofMinutes(23),
        remark = "了解清楚随机一段废话到底是一种怎么样的存在, 是解决一切问题的关键.经过上述讨论, 我们都知道, 只要有意义, 那么就必须慎重考虑.就我个人来说, 随机一段废话对我的意义, 不能不说非常重大. 可是，即使是这样，随机一段废话的出现仍然代表了一定的意义. 随机一段废话的发生, 到底需要如何做到, 不随机一段废话的发生, 又会如何产生. 对我个人而言，x不仅仅是一个重大的事件，还可能会改变我的人生.",
        tag = "当前核心"
    )



    val chainEvent = ChainEvent(
        startTime = LocalDateTime.now(),
        childEvents = listOf(internalEvent1, internalEvent2, internalEvent3),
        endTime = LocalDateTime.now(),
        category = "时光流开发",
    )


    ExploratoryRecordItem(chainEvent = chainEvent)

}