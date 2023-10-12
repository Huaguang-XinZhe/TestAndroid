package com.huaguang.testandroid

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
    fun provideRecordPageState(): RecordPageState {
        return RecordPageState()
    }

    @Singleton
    @Provides
    fun provideInternalItemState(): InternalItemState {
        return InternalItemState()
    }

    @Singleton
    @Provides
    fun provideInputState(): InputState {
        return InputState()
    }

}