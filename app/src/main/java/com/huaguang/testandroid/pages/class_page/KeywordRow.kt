package com.huaguang.testandroid.pages.class_page

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.huaguang.testandroid.data.entities.Keyword
import com.huaguang.testandroid.widget.LongPressOutlinedButton

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
                viewModel.onKeywordLongClick(keyword)
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

