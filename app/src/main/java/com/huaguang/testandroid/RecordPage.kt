package com.huaguang.testandroid

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout

@Composable
fun RecordPage() {
    ConstraintLayout(
        modifier = Modifier.fillMaxSize()
    ) {
        val (itemRef, regulatorRef, buttonsRef) = createRefs()

//        ExploratoryRecordItem(
//            chainEvent = ,
//            modifier = Modifier.constrainAs(itemRef) {
//                top.linkTo(parent.top)
//            }
//        )

        EventButtonsBar(
            modifier = Modifier.constrainAs(buttonsRef) {
                bottom.linkTo(parent.bottom, 15.dp)
            }
        )

//        TimeRegulatorBar(
//            modifier = Modifier.constrainAs(regulatorRef) {
//                bottom.linkTo(buttonsRef.top, 5.dp)
//            }
//        )
    }
}

@Preview(showBackground = true)
@Composable
fun RecordPageTest() {
    RecordPage()
}