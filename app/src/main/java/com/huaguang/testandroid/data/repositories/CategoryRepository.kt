package com.huaguang.testandroid.data.repositories

import com.huaguang.testandroid.cache.CategoryCache
import com.huaguang.testandroid.data.daos.CategoryDao
import com.huaguang.testandroid.data.entities.Category
import com.huaguang.testandroid.data.entities.Keyword
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CategoryRepository(private val categoryDao: CategoryDao) {

    suspend fun insertCategory(category: Category) = categoryDao.insert(category)

    /**
     * 获取没有子类属的类属。包括底层类属和那些没有父类属的类属。
     */
    suspend fun getCategoriesWithoutChildren() = categoryDao.getCategoriesWithoutChildren()

    suspend fun findCategoryIdByName(name: String): Long? {
        return categoryDao.findCategoryIdByName(name)
    }

    suspend fun getCategoryById(id: Long): Category? = categoryDao.getCategoryById(id)
    suspend fun deleteCategory(category: Category) = categoryDao.delete(category)

    /**
     * 根据类属 id 更新类属名。
     */
    suspend fun updateCategoryName(id: Long, newName: String) {
        categoryDao.updateCategoryNameById(id, newName)
    }

    /**
     * 根据类属名称获取类属 ID。
     *
     * 如果在缓存中未找到，则在类属表中插入一个新的类属，并返回其ID，然后更新缓存。
     */
    suspend fun retrieveCategoryId(categoryName: String?): Long? {
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

    fun getAllCategoriesFlow() = categoryDao.getAllCategoriesFlow()

    fun getCategoryToKeywordsMapFlow(): Flow<Map<Category, List<Keyword>>> {
        return categoryDao.getCategoriesWithKeywordsFlow()
            .map { categoriesWithKeywords ->
                categoriesWithKeywords.associateBy({ it.category }, { it.keywords })
            }
    }

}