package com.huaguang.testandroid.data.repositories

import com.huaguang.testandroid.data.entities.Category

class MainRepository(
    private val eventRepository: EventRepository,
    private val categoryRepository: CategoryRepository,
    private val tagRepository: TagRepository,
    private val keywordRepository: KeywordRepository
) {

    /**
     * 插入类属和关键词。
     *
     * @param categoryName 类属名称
     * @param keywords 类属对应的关键词列表
     */
    suspend fun insertCategoryAndKeywords(categoryName: String, keywords: List<String>) {
        // 1. 插入类属
        val categoryId = categoryRepository.insertCategory(Category(name = categoryName))
        // 2. 插入关键词
        keywordRepository.insertKeywords(keywords, categoryId)
    }

}
