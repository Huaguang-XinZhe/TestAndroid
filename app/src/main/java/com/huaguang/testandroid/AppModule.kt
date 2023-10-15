package com.huaguang.testandroid

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.huaguang.testandroid.buttons_bar.ButtonsBarState
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

}