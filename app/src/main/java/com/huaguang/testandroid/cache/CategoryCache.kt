package com.huaguang.testandroid.cache

import com.huaguang.testandroid.data.repositories.CategoryRepository

object CategoryCache {
    private var categoryNameToIdMap: Map<String, Int>? = null

    suspend fun initCache(categoryRepository: CategoryRepository) {
        if (categoryNameToIdMap == null) {
            categoryNameToIdMap = categoryRepository.getCategoriesWithoutChildren()
                .associate { it.name to it.id }
        }
    }

    fun getIdForCategoryName(categoryName: String): Int? {
        return categoryNameToIdMap?.get(categoryName)
    }

    // Call this method whenever there's a change in categories
    fun invalidateCache() {
        categoryNameToIdMap = null
    }
}
