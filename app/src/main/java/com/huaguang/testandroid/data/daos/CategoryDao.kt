package com.huaguang.testandroid.data.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.huaguang.testandroid.data.entities.Category
import com.huaguang.testandroid.dtos.CategoryWithKeywords
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(category: Category): Long


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

    /**
     * 根据类属 id 更新类属名。
     */
    @Query("""
        UPDATE categories
        SET name = :newName
        WHERE id = :id
    """)
    suspend fun updateCategoryNameById(id: Long, newName: String)

    @Query("SELECT id FROM categories WHERE name = :name")
    suspend fun findCategoryIdByName(name: String): Long?

    @Query("SELECT * FROM categories WHERE id = :categoryId")
    suspend fun getCategoryById(categoryId: Long): Category?

    @Delete
    suspend fun delete(category: Category)

    @Query("SELECT * FROM categories")
    fun getAllCategoriesFlow(): Flow<List<Category>>

    /**
     * 仅获取有关键词的类属和其对应的关键词。
     *
     * 没有关键词的类属不会返回到结果集中。
     */
    @Transaction
    @Query("""
        SELECT DISTINCT categories.* 
        FROM categories 
        JOIN keywords ON categories.id = keywords.categoryId
    """)
    fun getCategoriesWithKeywordsFlow(): Flow<List<CategoryWithKeywords>>




}