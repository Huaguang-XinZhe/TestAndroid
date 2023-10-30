package com.huaguang.testandroid.data.repositories

import com.huaguang.testandroid.data.daos.TagDao
import com.huaguang.testandroid.data.entities.Tag

class TagRepository(private val tagDao: TagDao) {
    suspend fun insertTag(tag: Tag) = tagDao.insert(tag)
    suspend fun getAllTags(): List<Tag> = tagDao.getAllTags()
    suspend fun getTagById(tagId: Int): Tag? = tagDao.getTagById(tagId)
    suspend fun deleteTag(tag: Tag) = tagDao.delete(tag)

    /**
     * 一次性插入多个 tag。
     *
     * @param tags 要插入的标签名列表。
     */
    suspend fun insertTags(tags: List<String>): List<Long> {
        val tagEntities = tags.map { Tag(name = it) }
        return tagDao.insertTags(tagEntities)
    }
}