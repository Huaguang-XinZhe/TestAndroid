package com.huaguang.testandroid.data.repositories

import com.huaguang.testandroid.data.daos.TagDao
import com.huaguang.testandroid.data.entities.Tag

class TagRepository(private val tagDao: TagDao) {
    suspend fun insertTag(tag: Tag) = tagDao.insert(tag)
    suspend fun getAllTags(): List<Tag> = tagDao.getAllTags()
    suspend fun getTagById(tagId: Int): Tag? = tagDao.getTagById(tagId)
    suspend fun deleteTag(tag: Tag) = tagDao.delete(tag)
    // ... other tag related methods
}