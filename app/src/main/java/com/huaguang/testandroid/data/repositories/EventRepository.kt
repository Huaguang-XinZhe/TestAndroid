package com.huaguang.testandroid.data.repositories

import com.huaguang.testandroid.data.daos.EventDao
import com.huaguang.testandroid.data.entities.Event
import java.time.Duration
import java.time.LocalDateTime

class EventRepository(private val eventDao: EventDao) {
    suspend fun insertEvent(event: Event) = eventDao.insert(event)
    suspend fun getAllEvents(): List<Event> = eventDao.getAllEvents()
    suspend fun getEventById(eventId: Int): Event? = eventDao.getEventById(eventId)

    /**
     * 根据事件 id 更新事件名称、备注和类属 id
     */
    suspend fun updNameRemarkAndCategory(
        eventId: Int,
        name: String,
        remark: String,
        categoryId: Int?
    ) {
        eventDao.updateNameRemarkAndCategoryById(eventId, name, remark, categoryId)
    }

    /**
     * 根据事件 id 更新事件结束时间和持续时间
     */
    suspend fun updEndTimeAndDuration(
        id: Int,
        endTime: LocalDateTime,
        duration: Duration
    ) {
        eventDao.updateEndTimeAndDurationById(id, endTime, duration)
    }


    suspend fun deleteEvent(event: Event) = eventDao.delete(event)
    // ... other event related methods
}