package com.huaguang.testandroid.data.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
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
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertKeyword(keyword: Keyword): Long

    // 插入多个关键词
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertKeywords(keywords: List<Keyword>)

    // 更新单个关键词
    @Update
    suspend fun updateKeyword(keyword: Keyword)

    // 删除单个关键词
    @Delete
    suspend fun deleteKeyword(keyword: Keyword)

    // 通过ID删除关键词
    @Query("DELETE FROM keywords WHERE id = :id")
    suspend fun deleteKeywordById(id: Int)


    // 通过类别ID查询所有关键词
    @Query("SELECT * FROM keywords WHERE categoryId = :categoryId")
    suspend fun findKeywordsByCategoryId(categoryId: Int): List<Keyword>
}