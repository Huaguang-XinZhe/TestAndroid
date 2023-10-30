package com.huaguang.testandroid.pages


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.huaguang.testandroid.dialog.InputAlertDialog
import com.huaguang.testandroid.viewmodels.ClassificationViewModel

@Composable
fun ClassificationPage(viewModel: ClassificationViewModel = viewModel()) {
    Column(modifier = Modifier.fillMaxSize()) {
//        // 分类部分
//        CategorySection()
//        Spacer(modifier = Modifier.height(16.dp))

        // 关键词部分
        KeywordSection()
    }

    InputAlertDialog(dialogState = viewModel.dialogState)

}


