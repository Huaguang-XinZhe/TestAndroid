package com.huaguang.testandroid

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.huaguang.testandroid.buttons_bar.ButtonsBarState
import com.huaguang.testandroid.classifier.ClassifierFactory
import com.huaguang.testandroid.data.AppDatabase
import com.huaguang.testandroid.data.daos.CategoryDao
import com.huaguang.testandroid.data.daos.EventDao
import com.huaguang.testandroid.data.daos.KeywordDao
import com.huaguang.testandroid.data.daos.TagDao
import com.huaguang.testandroid.data.repositories.CategoryRepository
import com.huaguang.testandroid.data.repositories.EventRepository
import com.huaguang.testandroid.data.repositories.KeywordRepository
import com.huaguang.testandroid.data.repositories.TagRepository
import com.huaguang.testandroid.input_field.InputState
import com.huaguang.testandroid.record_block.RecordBlockState
import com.huaguang.testandroid.record_block.TimeCache
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class) // TODO: 为什么引入这一句？
object AppModule {

    @Singleton
    @Provides
    fun providePageState(): RecordPageState {
        return RecordPageState()
    }

    @Singleton
    @Provides
    fun provideButtonsBarState(): MutableState<ButtonsBarState> {
        return mutableStateOf(ButtonsBarState.Default)
    }

    @Singleton
    @Provides
    fun provideInternalItemState(): RecordBlockState {
        return RecordBlockState()
    }

    @Singleton
    @Provides
    fun provideInputState(): InputState {
        return InputState()
    }

    @Singleton
    @Provides
    fun provideTimeCache(): TimeCache {
        return TimeCache()
    }

    @Singleton
    @Provides
    fun provideSharedState(): SharedState {
        return SharedState()
    }

    @Singleton
    @Provides
    fun provideAppDatabase(application: MyApplication): AppDatabase {
        return application.database
    }

    @Singleton
    @Provides
    fun provideEventDao(appDatabase: AppDatabase) = appDatabase.eventDao()

    @Singleton
    @Provides
    fun provideCategoryDao(appDatabase: AppDatabase) = appDatabase.classDao()

    @Singleton
    @Provides
    fun provideTagDao(appDatabase: AppDatabase) = appDatabase.tagDao()

    @Singleton
    @Provides
    fun provideEventTagMappingDao(appDatabase: AppDatabase) = appDatabase.eventTagMappingDao()

    @Singleton
    @Provides
    fun provideKeywordDao(appDatabase: AppDatabase) = appDatabase.keywordDao()

    @Singleton
    @Provides
    fun provideEventRepository(eventDao: EventDao): EventRepository {
        return EventRepository(eventDao)
    }

    @Singleton
    @Provides
    fun provideCategoryRepository(categoryDao: CategoryDao): CategoryRepository {
        return CategoryRepository(categoryDao)
    }

    @Singleton
    @Provides
    fun provideTagRepository(tagDao: TagDao): TagRepository {
        return TagRepository(tagDao)
    }

    @Singleton
    @Provides
    fun provideKeywordRepository(keywordDao: KeywordDao): KeywordRepository {
        return KeywordRepository(keywordDao)
    }

    @Provides
    fun provideClassifierFactory(keywordRepository: KeywordRepository): ClassifierFactory {
        return ClassifierFactory(keywordRepository)
    }

}