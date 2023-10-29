package com.huaguang.testandroid.data.repositories

import com.huaguang.testandroid.data.daos.KeywordDao
import com.huaguang.testandroid.data.entities.Keyword
import com.huaguang.testandroid.dtos.KeywordWithCategory

class KeywordRepository(private val keywordDao: KeywordDao) {

    // 获取所有关键词及其关联的类别
    suspend fun getAllKeywordsWithCategories(): List<KeywordWithCategory> {
        return keywordDao.getAllKeywordsWithCategories()
    }

    // 插入单个关键词
    suspend fun insertKeyword(keyword: Keyword): Long {
        return keywordDao.insertKeyword(keyword)
    }

    // 插入多个关键词
    suspend fun insertKeywords(keywords: List<Keyword>) {
        keywordDao.insertKeywords(keywords)
    }

    // 更新单个关键词
    suspend fun updateKeyword(keyword: Keyword) {
        keywordDao.updateKeyword(keyword)
    }

    // 删除单个关键词
    suspend fun deleteKeyword(keyword: Keyword) {
        keywordDao.deleteKeyword(keyword)
    }

    // 通过ID删除关键词
    suspend fun deleteKeywordById(id: Int) {
        keywordDao.deleteKeywordById(id)
    }

    // 通过类别ID查询所有关键词
    suspend fun findKeywordsByCategoryId(categoryId: Int): List<Keyword> {
        return keywordDao.findKeywordsByCategoryId(categoryId)
    }

}

