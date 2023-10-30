package com.huaguang.testandroid.data.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.huaguang.testandroid.data.entities.Event
import java.time.Duration
import java.time.LocalDateTime


@Dao
interface EventDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(event: Event): Long

    @Query("SELECT * FROM events")
    suspend fun getAllEvents(): List<Event>

    @Query("SELECT * FROM events WHERE id = :eventId")
    suspend fun getEventById(eventId: Long): Event?

    /**
     * 根据事件 id 获取类属 id。
     */
    @Query("SELECT categoryId FROM events WHERE id = :id")
    suspend fun getCategoryIdById(id: Long): Long?

    /**
     * 根据事件 id 更新事件名称、备注和类属 id
     */
    @Query("""
        UPDATE events
        SET name = :name, remark = :remark, categoryId = :categoryId
        WHERE id = :eventId
    """)
    suspend fun updateNameRemarkAndCategoryById(eventId: Long, name: String, remark: String, categoryId: Long?)

    /**
     * 根据事件 id 更新事件结束时间和持续时间
     */
    @Query("""
        UPDATE events
        SET endTime = :endTime, duration = :duration
        WHERE id = :id
    """)
    suspend fun updateEndTimeAndDurationById(id: Long, endTime: LocalDateTime, duration: Duration)

    /**
     * 根据事件 id 更新类属 id。
     */
    @Query("""
        UPDATE events
        SET categoryId = :categoryId
        WHERE id = :eventId
    """)
    suspend fun updateCategoryIdById(eventId: Long, categoryId: Long)

    @Delete
    suspend fun delete(event: Event)

}