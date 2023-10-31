package com.huaguang.testandroid.pages.class_page

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.huaguang.testandroid.data.entities.Category

@Composable
fun CategoryItem(
    category: Category,
    subCategories: List<Category>,
    viewModel: ClassificationViewModel = viewModel()
) {
    Column(modifier = Modifier.padding(8.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = category.name)
            IconButton(onClick = { viewModel.onAddCategoryClick(category.id) }) {
                Icon(Icons.Default.Add, contentDescription = null)
            }
        }
        subCategories.forEach {
            CategoryItem(category = it, subCategories = viewModel.getSubCategories(it.id))
        }
    }
}

