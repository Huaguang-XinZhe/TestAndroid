package com.huaguang.testandroid.pages

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.huaguang.testandroid.TAG
import com.huaguang.testandroid.data.entities.Keyword
import com.huaguang.testandroid.viewmodels.ClassificationViewModel
import com.huaguang.testandroid.widget.LongPressOutlinedButton

@Composable
fun KeywordSection(viewModel: ClassificationViewModel = viewModel()) {
    val categoryToKeywordsMap by viewModel.categoryToKeywordsMapFlow.collectAsState(initial = emptyMap())
    Log.d(TAG, "KeywordSection: categoryToKeywordsMap = $categoryToKeywordsMap")

    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(categoryToKeywordsMap.entries.toList()) { entry ->
            CategoryText(name = entry.key.name)
            KeywordRow(
                categoryId = entry.key.id,
                keywords = entry.value
            )
        }

    }
}


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun KeywordRow(
    categoryId: Long,
    keywords: List<Keyword>,
    viewModel: ClassificationViewModel = viewModel()
) {
    FlowRow(
        modifier = Modifier.padding(16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        keywords.forEach { keyword ->
            KeywordButton(
                keyword = keyword,
                onClick = { viewModel.onKeywordClick(keyword) },
            ) {
                // 长按删除
            }
        }

        OutlinedButton(onClick = { viewModel.onAddKeywordClick(categoryId) }) {
            Text(text = "+")
        }
    }
}

@Composable
fun CategoryText(name: String) {
    Text(
        text = name,
        fontSize = 20.sp,
        fontWeight = FontWeight.SemiBold,
        modifier = Modifier.padding(16.dp)
    )
}

@Composable
fun KeywordButton(
    keyword: Keyword,
    onClick: () -> Unit,
    onLongClick: () -> Unit
) {
    LongPressOutlinedButton(
        modifier = Modifier.padding(4.dp),
        text = keyword.name,
        onClick = onClick,
        onLongClick = onLongClick,
    )
}

