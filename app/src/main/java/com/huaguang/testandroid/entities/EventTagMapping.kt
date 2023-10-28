package com.huaguang.testandroid.entities

import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    tableName = "event_tag_mapping",
    primaryKeys = ["eventId", "tagId"], // 复合主键
    foreignKeys = [
        ForeignKey(
            entity = Event::class,
            parentColumns = ["id"],
            childColumns = ["eventId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Tag::class,
            parentColumns = ["id"],
            childColumns = ["tagId"],
            onDelete = ForeignKey.SET_NULL
        )
    ]
)
data class EventTagMapping(
    val eventId: Int,
    val tagId: Int
)
