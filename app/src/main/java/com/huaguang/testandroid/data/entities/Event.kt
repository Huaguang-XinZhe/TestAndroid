package com.huaguang.testandroid.data.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime

@Entity(
    tableName = "events",
    foreignKeys = [ForeignKey(
        entity = Category::class,
        parentColumns = ["id"],
        childColumns = ["categoryId"],
        onDelete = ForeignKey.SET_NULL
    )]
)
data class Event(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0, // 开始新事件时自动生成，无需指定
    val name: String, // 一般在输入确认后确立，但也可在开启新事件时确立
    val startTime: LocalDateTime, // 开始新事件时确立
    var endTime: LocalDateTime? = null,
    val duration: Duration? = null,
    val interval: Int, // 开始新事件时确立
    val remark: String? = null, // 伴随 name 确立，如果 name 提前指定，那 remark 应该为 null
    var categoryId: Int?, // 伴随 name 确立
    val eventDate: LocalDate, // 伴随 startTime 确立
)