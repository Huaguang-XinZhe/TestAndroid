package com.huaguang.testandroid

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun TimeRegulatorBar(
    modifier: Modifier = Modifier,
    viewModel: RecordPageViewModel = viewModel()
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        TimeAdjustButton(-5, "-5m")

        TimeAdjustIconButton(-1, R.drawable.minus)

        IconButton(onClick = { viewModel.onHideButtonClick() }) {
            Icon(
                painter = painterResource(id = R.drawable.arrow_down),
                contentDescription = null,
                tint = Color.DarkGray.copy(alpha = 0.6f),
                modifier = Modifier
                    .size(36.dp)
                    .padding(top = 12.dp)
            )
        }

        TimeAdjustIconButton(1, R.drawable.add_circle)

        TimeAdjustButton(5, "+5m")
    }
}

@Composable
fun TimeAdjustButton(value: Long, label: String) {
    TextButton(onClick = {  }) {
        Text(label)
    }
}

@Composable
fun TimeAdjustIconButton(value: Long, iconRes: Int) {
    IconButton(onClick = {  }) {
        Icon(
            painter = painterResource(id = iconRes),
            contentDescription = null,
            modifier = Modifier.size(24.dp),
            tint = MaterialTheme.colorScheme.primary
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TimeRegulatorTest() {
    TimeRegulatorBar(modifier = Modifier.padding(10.dp))
}