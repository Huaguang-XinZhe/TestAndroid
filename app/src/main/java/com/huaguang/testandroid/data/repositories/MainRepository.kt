package com.huaguang.testandroid.data.repositories

import androidx.room.Transaction
import com.huaguang.testandroid.data.entities.Category
import com.huaguang.testandroid.data.entities.EventTagMapping

class MainRepository(
    private val eventRepository: EventRepository,
    private val categoryRepository: CategoryRepository,
    private val tagRepository: TagRepository,
    private val keywordRepository: KeywordRepository,
    private val eventTagMappingRepository: EventTagMappingRepository
) {

    /**
     * 插入类属和关键词。
     *
     * @param categoryName 类属名称
     * @param keywords 类属对应的关键词列表
     */
    @Transaction
    suspend fun insertCategoryAndKeywords(categoryName: String, keywords: List<String>) {
        // 1. 插入类属
        val categoryId = categoryRepository.insertCategory(Category(name = categoryName))
        // 2. 插入关键词
        keywordRepository.insertKeywords(keywords, categoryId)
    }

    /**
     * 新增一个类属。
     *
     * 在类属表插入一个类属，然后将得到的 id 更新到事件表中。这就需要拿到事件的 id。
     */
    @Transaction
    suspend fun addCategory(categoryName: String, eventId: Long) {
        // 1. 插入类属
        val categoryId = categoryRepository.insertCategory(Category(name = categoryName))
        // 2. 更新事件表
        eventRepository.updateCategoryId(eventId, categoryId)
    }

    /**
     * 更新一个类属。
     *
     * 传入事件 id，根据这个 id 找到类属 id，然后再根据类属 id 去更新类属名。
     */
    @Transaction
    suspend fun updateCategory(eventId: Long, categoryName: String) {
        // 1. 根据事件 id 获取类属 id
        val categoryId = eventRepository.getCategoryId(eventId)
        // 2. 更新类属名
        if (categoryId != null) { // 在这个特定场景下，categoryId 其实不会为 null
            categoryRepository.updateCategoryName(categoryId, categoryName)
        }
    }

    /**
     * 一次新增多个标签。
     *
     * 批量插入多个标签，得到这些标签的 id 列表；
     * 然后拿着一个固定的事件 id（传入）和这些标签 id 列表，构建事件-标签映射的列表；
     * 最后一次性插入这些映射。
     */
    @Transaction
    suspend fun addTags(tags: List<String>, eventId: Long) {
        // 1. 批量插入标签
        val tagIds = tagRepository.insertTags(tags)
        // 2. 构建事件-标签映射列表
        val eventTagMappings = tagIds.map { tagId -> EventTagMapping(eventId, tagId) }
        // 3. 一次性插入映射
        eventTagMappingRepository.insertEventTagMappings(eventTagMappings)
    }

}