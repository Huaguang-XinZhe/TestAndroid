package com.huaguang.testandroid

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ExploratoryRecordItem(
    chainEvent: ChainEvent,
    onTagLabelClick: () -> Unit,
) {
    chainEvent.apply {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
        ) {
            TimeLabel(text = startTime.format()) {  }

            childEvents.forEach { internalEvent ->
                Text(
                    text = internalEvent.name,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier
                        .padding(5.dp)
                        .fillMaxWidth()
                )

                Card(
                    modifier = Modifier
                        .padding(horizontal = 10.dp, vertical = 5.dp)
                        .fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.LightGray
                    )
                ) {
                    Text(
                        text = internalEvent.remark,
                    )
                }

                if (internalEvent.tag != null) {
                    Label(
                        type = LabelType.Tag(
                            name = internalEvent.tag,
                            onClick = onTagLabelClick
                        ),
                        modifier = Modifier.padding(10.dp)
                    )
                }

            }

            if (endTime != null) {
                TimeLabel(text = endTime.format()) {  }
            }
        }
    }
}




@Preview(showBackground = true)
@Composable
fun ExploratoryRecordItemTest() {

    Box(modifier = Modifier.padding(10.dp)) {
        TimeLabel(text = "12:30") {

        }
    }

}