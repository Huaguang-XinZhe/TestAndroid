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
import com.huaguang.testandroid.data.entities.Tag
import com.huaguang.testandroid.viewmodels.ClassificationViewModel

@Composable
fun TagSection(viewModel: ClassificationViewModel = viewModel()) {
    val tags = remember { viewModel.tags }
    LaunchedEffect(Unit) {
        viewModel.fetchTags()
    }

    LazyColumn {
        items(tags) { tag ->
            TagItem(tag, onDelete = viewModel::deleteTag)
        }
    }
}

@Composable
fun TagItem(tag: Tag, onDelete: (Tag) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = tag.name)
        IconButton(onClick = { onDelete(tag) }) {
            Icon(Icons.Default.Delete, contentDescription = null)
        }
    }
}