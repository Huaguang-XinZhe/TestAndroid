package com.huaguang.testandroid.data.repositories

import com.huaguang.testandroid.data.daos.KeywordDao
import com.huaguang.testandroid.data.entities.Keyword
import com.huaguang.testandroid.dtos.KeywordWithCategory

class KeywordRepository(private val keywordDao: KeywordDao) {

    // 获取所有关键词及其关联的类别
    suspend fun getAllKeywordsWithCategories(): List<KeywordWithCategory> {
        return keywordDao.getAllKeywordsWithCategories()
    }


    /**
     * 批量插入多个关键词。
     *
     * @param keywords 关键词列表
     * @param categoryId 关键词所属的类别ID
     */
    suspend fun insertKeywords(keywords: List<String>, categoryId: Long) {
        keywords.map { keyword ->
            Keyword(name = keyword, categoryId = categoryId)
        }.also { keywordDao.insertKeywords(it) }
    }

    // 删除单个关键词
    suspend fun deleteKeyword(keyword: Keyword) {
        keywordDao.deleteKeyword(keyword)
    }


    // 通过类别ID查询所有关键词
    suspend fun findKeywordsByCategoryId(categoryId: Int): List<Keyword> {
        return keywordDao.findKeywordsByCategoryId(categoryId)
    }

    /**
     * 通过 id 更新关键词的名称。
     */
    suspend fun updateKeywordName(id: Long, newName: String) {
        keywordDao.updateKeywordNameById(id, newName)
    }


}

