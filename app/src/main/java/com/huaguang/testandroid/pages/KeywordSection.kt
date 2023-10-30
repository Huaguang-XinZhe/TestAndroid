package com.huaguang.testandroid.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.huaguang.testandroid.data.entities.Keyword
import com.huaguang.testandroid.viewmodels.ClassificationViewModel

@Composable
fun KeywordSection(viewModel: ClassificationViewModel = viewModel()) {
    val keywords = remember { viewModel.keywords }
    LaunchedEffect(Unit) {
        viewModel.fetchKeywords()
    }

    LazyColumn {
        items(keywords) { keyword ->
            KeywordItem(keyword, onDelete = viewModel::deleteKeyword)
        }
    }
}

@Composable
fun KeywordItem(keyword: Keyword, onDelete: (Keyword) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = keyword.name)
        IconButton(onClick = { onDelete(keyword) }) {
            Icon(Icons.Default.Delete, contentDescription = null)
        }
    }
}
