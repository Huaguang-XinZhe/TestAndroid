package com.huaguang.testandroid.dtos

import androidx.room.Embedded
import androidx.room.Relation
import com.huaguang.testandroid.data.entities.Category
import com.huaguang.testandroid.data.entities.Keyword

/**
 * 用于查询类别及其关联的关键词。
 *
 * 对每个类属去查其关联的关键词，是一对多的关系。
 */
data class CategoryWithKeywords(
    @Embedded val category: Category,
    @Relation(
        parentColumn = "id",
        entityColumn = "categoryId"
    )
    val keywords: List<Keyword>
)