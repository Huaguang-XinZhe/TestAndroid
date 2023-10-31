package com.huaguang.testandroid.pages.class_page


import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.huaguang.testandroid.dialog.InputAlertDialog

@Composable
fun ClassificationPage(viewModel: ClassificationViewModel = viewModel()) {
    // 主体部分
    ClassificationContent()

    InputAlertDialog(dialogState = viewModel.dialogState)

}



