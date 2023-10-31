package com.huaguang.testandroid.data.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.huaguang.testandroid.data.entities.Keyword
import com.huaguang.testandroid.dtos.KeywordWithCategory

@Dao
interface KeywordDao {
    @Query("""
        SELECT k.name as keyword, c.name as category
        FROM keywords k 
        JOIN categories c ON k.categoryId = c.id
    """)
    suspend fun getAllKeywordsWithCategories(): List<KeywordWithCategory>

    // 插入单个关键词
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertKeyword(keyword: Keyword): Long

    // 插入多个关键词
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertKeywords(keywords: List<Keyword>)

    // 删除单个关键词
    @Delete
    suspend fun deleteKeyword(keyword: Keyword)


    // 通过类别ID查询所有关键词
    @Query("SELECT * FROM keywords WHERE categoryId = :categoryId")
    suspend fun findKeywordsByCategoryId(categoryId: Int): List<Keyword>

    /**
     * 通过 id 更新关键词的名称。
     */
    @Query("UPDATE keywords SET name = :newName WHERE id = :id")
    suspend fun updateKeywordNameById(id: Long, newName: String)
}