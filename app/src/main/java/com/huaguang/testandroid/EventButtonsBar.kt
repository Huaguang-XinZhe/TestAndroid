package com.huaguang.testandroid

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun EventButtonsBar(
    modifier: Modifier = Modifier,
    viewModel: ButtonsViewModel = viewModel(),
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier.fillMaxWidth()
    ) {
        // 撤销按钮，点击撤销（只存了三步，三步后自动消失）
        IconButton(
            onClick = { viewModel.undoButtonClick() },
            modifier = modifier.padding(start = 10.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.undo_filled),
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {
            // 新增按钮，点击——在当前主题事项下新增或结束一个事项，双击——结束当前新增，并另外开启一个新增事项，间隔一分钟，也就是连续的意思
            DoubleClickButton(
                text = "新增",
                onClick = { viewModel.onAddButtonClick() },
            ) { viewModel.onAddButtonDoubleClick() }
            
            Spacer(modifier = Modifier.width(5.dp))

            // 插入按钮，在当前主题事项下插入一个事件
            TextButton(onClick = { /*TODO*/ }) {
                Text(text = "插入")
            }
        }

        // 点击停止当前主题事件，双击开启下一主题事件，间隔 1 分钟
        DoubleClickIconButton(
            iconRes = R.drawable.stop,
            onClick = { viewModel.onStopButtonClick() },
            modifier = modifier.padding(end = 10.dp)
        ) { viewModel.onStopButtonDoubleClick() }
    }
}



@Preview(showBackground = true)
@Composable
fun EventButtonsTest() {

//    EventButtons()

}