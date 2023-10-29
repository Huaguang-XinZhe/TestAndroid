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
    suspend fun insert(category: Category): Int

    @Query("SELECT * FROM categories")
    suspend fun getAllCategories(): List<Category>


    /**
     * 获取没有子类属的类属。
     */
    @Query("""
        SELECT c1.id, c1.name
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