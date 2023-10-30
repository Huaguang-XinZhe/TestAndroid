package com.huaguang.testandroid.data.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.huaguang.testandroid.data.entities.EventTagMapping

@Dao
interface EventTagMappingDao {
    @Insert
    suspend fun insert(eventTagMapping: EventTagMapping)

    @Query("""
        SELECT t.name 
        FROM tags t 
        JOIN event_tag_mapping etm ON t.id = etm.tagId
        WHERE etm.eventId = :eventId
    """)
    suspend fun getTagsForEvent(eventId: Int): List<String>

    /**
     * 一次性插入多个映射。
     */
    @Insert
    suspend fun insertEventTagMappings(eventTagMappings: List<EventTagMapping>)

}