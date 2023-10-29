package com.huaguang.testandroid.data.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

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
    ],
    indices = [Index("eventId"), Index("tagId")]
)
data class EventTagMapping(
    val eventId: Int,
    val tagId: Int
)
