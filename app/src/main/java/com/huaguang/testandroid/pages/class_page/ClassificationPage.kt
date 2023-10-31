package com.huaguang.testandroid.pages.class_page


import androidx.compose.runtime.Composable
import com.huaguang.testandroid.dialog.ConfirmDeleteDialog
import com.huaguang.testandroid.dialog.InputAlertDialog

@Composable
fun ClassificationPage(viewModel: ClassificationViewModel) {
    // 主体部分
    ClassificationContent()

    // 输入对话框
    InputAlertDialog(inputDialogState = viewModel.inputDialogState)

    // 删除确认对话框
    ConfirmDeleteDialog(deleteDialogState = viewModel.deleteDialogState)


}



