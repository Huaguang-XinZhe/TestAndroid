package com.huaguang.testandroid.data.repositories

import androidx.room.Transaction
import com.huaguang.testandroid.data.entities.Category

class MainRepository(
    private val eventRepository: EventRepository,
    private val categoryRepository: CategoryRepository,
    private val tagRepository: TagRepository
) {
    /**
     * 根据事件 id 更新事件名称、备注和类属 id
     * @param categoryName 类属名称 可为空值，如果为空，那么类属 id 也为空
     */
    @Transaction
    suspend fun updateEventWithCategory(
        eventId: Int,
        name: String,
        remark: String,
        categoryName: String?
    ) {
        // 根据类属名称获取ID或者插入新类属并获取ID
        val categoryId = if (categoryName != null) {
            categoryRepository.findCategoryIdByName(categoryName) ?: categoryRepository.insertCategory(
                Category(name = categoryName, parentId = null)
            )
        } else {
            null
        }

        // 更新事件
        eventRepository.updateEventFieldsById(eventId, name, remark, categoryId)
    }
}
