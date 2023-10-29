package com.huaguang.testandroid

import android.app.Application
import androidx.room.Room
import com.huaguang.testandroid.data.AppDatabase
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class MyApplication @Inject constructor() : Application() {

    val database: AppDatabase by lazy {
        Room.databaseBuilder(
            context = this,
            klass = AppDatabase::class.java,
            name = "app_database"
        ).build()
    }

}