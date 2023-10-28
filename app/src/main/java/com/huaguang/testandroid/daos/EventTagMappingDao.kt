package com.huaguang.testandroid.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.huaguang.testandroid.entities.EventTagMapping

@Dao
interface EventTagMappingDao {
    @Insert
    fun insert(eventTagMapping: EventTagMapping)

    @Query("""
        SELECT t.name 
        FROM tags t 
        JOIN event_tag_mapping etm ON t.id = etm.tagId
        WHERE etm.eventId = :eventId
    """)
    fun getTagsForEvent(eventId: Int): List<String>
}