package com.huaguang.testandroid.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.time.Duration
import java.time.LocalDateTime

@Entity(
    tableName = "events",
    foreignKeys = [ForeignKey(
        entity = Category::class,
        parentColumns = ["id"],
        childColumns = ["classId"],
        onDelete = ForeignKey.SET_NULL
    )]
)
data class Event(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val startTime: LocalDateTime,
    var endTime: LocalDateTime? = null,
    val duration: Duration? = null,
    val interval: Int = 0,
    val remark: String? = null,
    val classId: Int?,
)