package com.huaguang.testandroid.pages.class_page

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun ClassificationContent(
    modifier: Modifier = Modifier,
    viewModel: ClassificationViewModel = viewModel()
) {
    val categoryToKeywordsMap by viewModel.categoryToKeywordsMapFlow.collectAsState(initial = emptyMap())

    LazyColumn(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        contentPadding = PaddingValues(vertical = 16.dp)
    ) {
        // 分类部分
        items(viewModel.rootCategories) { category ->
            CategoryItem(
                category = category,
                subCategories = viewModel.getSubCategories(category.id)
            )
        }

        // 关键词部分
        items(categoryToKeywordsMap.entries.toList()) { entry ->
            CategoryText(name = entry.key.name)
            KeywordRow(
                categoryId = entry.key.id,
                keywords = entry.value
            )
        }
    }
}