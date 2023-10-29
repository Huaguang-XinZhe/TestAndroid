package com.huaguang.testandroid.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.huaguang.testandroid.data.daos.CategoryDao
import com.huaguang.testandroid.data.daos.EventDao
import com.huaguang.testandroid.data.daos.EventTagMappingDao
import com.huaguang.testandroid.data.daos.KeywordDao
import com.huaguang.testandroid.data.daos.TagDao
import com.huaguang.testandroid.data.entities.Category
import com.huaguang.testandroid.data.entities.Event
import com.huaguang.testandroid.data.entities.EventTagMapping
import com.huaguang.testandroid.data.entities.Keyword
import com.huaguang.testandroid.data.entities.Tag

@Database(
    entities = [Event::class, Category::class, Tag::class, EventTagMapping::class, Keyword::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun eventDao(): EventDao
    abstract fun classDao(): CategoryDao
    abstract fun tagDao(): TagDao
    abstract fun eventTagMappingDao(): EventTagMappingDao
    abstract fun keywordDao(): KeywordDao
}