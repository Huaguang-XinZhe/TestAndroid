package com.huaguang.testandroid.data.repositories

import com.huaguang.testandroid.data.daos.EventTagMappingDao
import com.huaguang.testandroid.data.entities.EventTagMapping

class EventTagMappingRepository(
    private val eventTagMappingDao: EventTagMappingDao
) {

    /**
     * 一次性插入多条事件-标签映射。
     */
    suspend fun insertEventTagMappings(eventTagMappings: List<EventTagMapping>) =
        eventTagMappingDao.insertEventTagMappings(eventTagMappings)

}