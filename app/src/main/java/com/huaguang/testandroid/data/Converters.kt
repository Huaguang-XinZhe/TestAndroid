package com.huaguang.testandroid.data

import androidx.room.TypeConverter
import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime

object Converters {

    @TypeConverter
    fun fromLocalDateTime(dateTime: LocalDateTime?): String? {
        return dateTime?.toString()
    }

    @TypeConverter
    fun toLocalDateTime(dateTimeString: String?): LocalDateTime? {
        return if (dateTimeString != null) {
            LocalDateTime.parse(dateTimeString)
        } else null
    }

    @TypeConverter
    fun fromDuration(duration: Duration?): Long? {
        return duration?.toMillis()
    }

    @TypeConverter
    fun toDuration(durationMillis: Long?): Duration? {
        return if (durationMillis != null) {
            Duration.ofMillis(durationMillis)
        } else null
    }

    @TypeConverter
    fun fromLocalDate(date: LocalDate?): String? {
        return date?.toString()
    }

    @TypeConverter
    fun toLocalDate(dateString: String?): LocalDate? {
        return if (dateString != null) {
            LocalDate.parse(dateString)
        } else null
    }


}

