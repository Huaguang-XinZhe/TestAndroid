package com.huaguang.testandroid.pages


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.huaguang.testandroid.viewmodels.ClassificationViewModel

@Composable
fun ClassificationPage(viewModel: ClassificationViewModel = viewModel()) {
    Column {
        // 分类部分
        CategorySection()
        Spacer(modifier = Modifier.height(16.dp))

//        // 标签部分
//        TagSection()
//        Spacer(modifier = Modifier.height(16.dp))
//
//        // 关键词部分
//        KeywordSection()
    }

    if (viewModel.showDialog.value) {
        AddCategoryDialog(
            onAdd = { viewModel.addCategory(it) },
            onDismiss = { viewModel.showDialog.value = false }
        )
    }

}


