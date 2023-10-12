package com.huaguang.testandroid

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
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
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun EventButtonsBar(
    modifier: Modifier = Modifier,
    viewModel: ButtonsViewModel = viewModel(),
) {
    viewModel.pageState.buttonsBarState.value.apply {
        if (!barShow) return

        ConstraintLayout( // 使用 Row，在按钮显隐变化时，不太好保证居中的问题
            modifier = modifier.fillMaxWidth()
        ) {
            val (undoButtonRef, centerButtonsRef, stopButtonRef) = createRefs()

            UndoButton(
                modifier = Modifier.constrainAs(undoButtonRef) {
                    start.linkTo(parent.start, 16.dp)
                    // 为了竖直居中
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.constrainAs(centerButtonsRef) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    // 为了竖直居中
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }
            ) {
                if (mainStartButtonShow) {
                    MainStartButton()
                }

                AddButton(text = addButtonText)

                if (addButtonText != null && insertButtonText != null) {
                    Spacer(modifier = Modifier.width(5.dp))
                }

                InsertButton(text = insertButtonText)
            }

            if (!mainStartButtonShow) {
                StopButton(
                    modifier = Modifier.constrainAs(stopButtonRef) {
                        end.linkTo(parent.end, 16.dp)
                        // 为了竖直居中
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    }
                )
            }

        }
    }
}

@Composable
fun MainStartButton(viewModel: ButtonsViewModel = viewModel()) {
    Button(
        onClick = { viewModel.onMainStartClick() },
    ) {
        Text(text = "开始")
    }
}

/**
 * 撤销按钮，点击撤销（只存了三步，三步后自动消失）
 */
@Composable
fun UndoButton(
    modifier: Modifier = Modifier,
    viewModel: ButtonsViewModel = viewModel()
) {
    IconButton(
        onClick = { viewModel.undoButtonClick() },
        modifier = modifier
    ) {
        Icon(
            painter = painterResource(id = R.drawable.undo_filled),
            contentDescription = null,
            modifier = Modifier.size(24.dp)
        )
    }
}

/**
 * 点击停止当前主题事件，双击开启下一主题事件，间隔 1 分钟
 */
@Composable
fun StopButton(
    modifier: Modifier = Modifier,
    viewModel: ButtonsViewModel = viewModel()
) {
    DoubleClickIconButton(
        iconRes = R.drawable.stop,
        onClick = { viewModel.onStopButtonClick() },
        modifier = modifier
    ) { viewModel.onStopButtonDoubleClick() }
}

/**
 * 新增按钮，点击——在当前主题事项下新增或结束一个事项；
 * 双击——结束当前新增，并另外开启一个新增事项，间隔一分钟，也就是连续的意思
 */
@Composable
fun AddButton(
    text: String?,
    viewModel: ButtonsViewModel = viewModel()
) {
    if (text == null) return

    DoubleClickButton(
        text = text,
        onClick = { viewModel.onAddButtonClick(text) },
    ) { viewModel.onAddButtonDoubleClick() }

}

/**
 * 插入按钮，在当前主题事项下插入一个事件
 */
@Composable
fun InsertButton(
    text: String?,
    viewModel: ButtonsViewModel = viewModel()
) {
    if (text == null) return

    TextButton(onClick = { viewModel.onInsertButtonClick(text) }) {
        Text(text = text)
    }
}

@Preview(showBackground = true)
@Composable
fun EventButtonsTest() {

//    EventButtons()

}