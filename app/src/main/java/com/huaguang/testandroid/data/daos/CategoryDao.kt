package com.huaguang.testandroid.data.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.huaguang.testandroid.data.entities.Category

@Dao
interface CategoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(category: Category): Long

    @Query("SELECT * FROM categories")
    suspend fun getAllCategories(): List<Category>

    /**
     * 获取没有子类属的类属。
     *
     * 注意！这里必须返回所有字段，如果省略 parentId 字段，而又没有使用 @Ignore 标记，那么 Room 就会报错。
     */
    @Query("""
        SELECT c1.*
        FROM categories c1
        LEFT JOIN categories c2 ON c1.id = c2.parentId
        WHERE c2.parentId IS NULL
    """)
    suspend fun getCategoriesWithoutChildren(): List<Category>

    @Query("SELECT id FROM categories WHERE name = :name")
    suspend fun findCategoryIdByName(name: String): Int?

    @Query("SELECT * FROM categories WHERE id = :categoryId")
    suspend fun getCategoryById(categoryId: Int): Category?

    @Delete
    suspend fun delete(category: Category)


}