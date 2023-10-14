package com.huaguang.testandroid

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
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

}