package com.huaguang.testandroid.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.huaguang.testandroid.entities.Category

@Dao
interface CategoryDao {
    @Insert
    fun insert(category: Category): Long

    @Query("SELECT * FROM categories WHERE id = :id")
    fun getClassById(id: Int): Category
}