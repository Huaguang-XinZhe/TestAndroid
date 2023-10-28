package com.huaguang.testandroid.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.huaguang.testandroid.entities.Tag

@Dao
interface TagDao {
    @Insert
    fun insert(tag: Tag): Long

    @Query("SELECT * FROM tags WHERE id = :id")
    fun getTagById(id: Int): Tag
}