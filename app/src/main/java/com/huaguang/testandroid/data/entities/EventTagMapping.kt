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
            onDelete = ForeignKey.CASCADE // 事件删除，那对应的映射也删除
        ),
        ForeignKey(
            entity = Tag::class,
            parentColumns = ["id"],
            childColumns = ["tagId"],
            onDelete = ForeignKey.SET_NULL // 标签删除，那对应的映射就置空
        )
    ],
    indices = [Index("eventId"), Index("tagId")]
)
data class EventTagMapping(
    val eventId: Long,
    val tagId: Long
)
