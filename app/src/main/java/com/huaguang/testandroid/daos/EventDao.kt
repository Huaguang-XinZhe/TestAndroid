package com.huaguang.testandroid.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.huaguang.testandroid.entities.Event


@Dao
interface EventDao {
    @Insert
    fun insert(event: Event): Long

    @Query("SELECT * FROM events WHERE id = :id")
    fun getEventById(id: Int): Event
}