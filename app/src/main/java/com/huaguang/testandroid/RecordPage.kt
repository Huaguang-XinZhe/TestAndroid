package com.huaguang.testandroid

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.huaguang.testandroid.buttons_bar.EventButtonsBar
import com.huaguang.testandroid.input_field.EventInputField
import com.huaguang.testandroid.record_block.ExploratoryRecordBlock

@Composable
fun RecordPage(
    viewModel: RecordPageViewModel
) {
    val events = viewModel.events
    val selected = viewModel.recordBlockState.timeLabelSelected.value
    val mainSelected = viewModel.recordBlockState.mainStartLabelSelected.value
    val buttonsBarShow = remember { mutableStateOf(true) }
    val regulatorBarShow = remember { mutableStateOf(false) }

    LaunchedEffect(selected, mainSelected) {
        if (selected || mainSelected) {
            buttonsBarShow.value = false
            regulatorBarShow.value = true
        } else {
            buttonsBarShow.value = true
            regulatorBarShow.value = false
        }
    }

    ConstraintLayout(
        modifier = Modifier.fillMaxSize()
    ) {
        val (itemRef, regulatorRef, buttonsRef) = createRefs()

        viewModel.pageState.apply {
            if (recordMode.value) {
                ExploratoryRecordBlock(
                    modifier = Modifier.constrainAs(itemRef) {
                        top.linkTo(parent.top, 15.dp)
                    },
                    events = events
                )
            } else {
                // TODO: 展示块
            }

            if (buttonsBarShow.value) {
                EventButtonsBar(
                    modifier = Modifier.constrainAs(buttonsRef) {
                        bottom.linkTo(parent.bottom, 15.dp)
                    }
                )
            }

            if (regulatorBarShow.value) {
                TimeRegulatorBar(
                    modifier = Modifier.constrainAs(regulatorRef) {
                        if (buttonsBarShow.value) {
                            bottom.linkTo(buttonsRef.top, 5.dp)
                        } else {
                            bottom.linkTo(parent.bottom, 15.dp)
                        }
                    }
                )
            }
        }
    }

    EventInputField(
        modifier = Modifier.padding(top = 450.dp)
    )
}

@Preview(showBackground = true)
@Composable
fun RecordPageTest() {
//    RecordPage()
}