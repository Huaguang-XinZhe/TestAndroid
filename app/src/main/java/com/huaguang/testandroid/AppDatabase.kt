package com.huaguang.testandroid

import androidx.room.Database
import androidx.room.RoomDatabase
import com.huaguang.testandroid.daos.CategoryDao
import com.huaguang.testandroid.daos.EventDao
import com.huaguang.testandroid.daos.EventTagMappingDao
import com.huaguang.testandroid.daos.TagDao
import com.huaguang.testandroid.entities.Category
import com.huaguang.testandroid.entities.Event
import com.huaguang.testandroid.entities.EventTagMapping
import com.huaguang.testandroid.entities.Tag

@Database(entities = [Event::class, Category::class, Tag::class, EventTagMapping::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun eventDao(): EventDao
    abstract fun classDao(): CategoryDao
    abstract fun tagDao(): TagDao
    abstract fun eventTagMappingDao(): EventTagMappingDao
}