package com.huaguang.testandroid.data.repositories

import com.huaguang.testandroid.cache.CategoryCache
import com.huaguang.testandroid.data.daos.CategoryDao
import com.huaguang.testandroid.data.entities.Category

class CategoryRepository(private val categoryDao: CategoryDao) {
    suspend fun insertCategory(category: Category) = categoryDao.insert(category)
    suspend fun getAllCategories(): List<Category> = categoryDao.getAllCategories()

    /**
     * 获取没有子类属的类属。
     */
    suspend fun getCategoriesWithoutChildren() = categoryDao.getCategoriesWithoutChildren()

    suspend fun findCategoryIdByName(name: String): Int? {
        return categoryDao.findCategoryIdByName(name)
    }

    suspend fun getCategoryById(categoryId: Int): Category? = categoryDao.getCategoryById(categoryId)
    suspend fun deleteCategory(category: Category) = categoryDao.delete(category)

    /**
     * 根据类属名称获取类属 ID。
     *
     * 如果在缓存中未找到，则在类属表中插入一个新的类属，并返回其ID，然后更新缓存。
     */
    suspend fun retrieveCategoryId(categoryName: String?): Int? {
        return if (categoryName != null) {
            // 从缓存中获取类属ID
            CategoryCache.getIdForCategoryName(categoryName) ?: run {
                // 如果在缓存中未找到，则在类属表中插入一个新的类属，并返回其ID
                val newCategory = Category(name = categoryName)
                val categoryId = insertCategory(newCategory)

                // 更新缓存
                CategoryCache.invalidateCache()
                CategoryCache.initCache(this) // 这里内部调用的其实是 getCategoriesWithoutChildren() 方法

                categoryId // run 块会返回一个值
            }
        } else null
    }

}