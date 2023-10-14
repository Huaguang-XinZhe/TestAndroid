package com.huaguang.testandroid

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun RecordPage(
    viewModel: RecordPageViewModel = viewModel()
) {
    val events = viewModel.events

    ConstraintLayout(
        modifier = Modifier.fillMaxSize()
    ) {
        val (itemRef, regulatorRef, buttonsRef) = createRefs()

        viewModel.pageState.apply {
            if (recordMode.value) {
                ExploratoryRecordBlock(
                    events = events,
                    modifier = Modifier.constrainAs(itemRef) {
                        top.linkTo(parent.top, 15.dp)
                    }
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
    RecordPage()
}