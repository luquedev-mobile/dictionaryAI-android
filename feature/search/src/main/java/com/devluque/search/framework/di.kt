package com.devluque.search.framework

import com.devluque.data.datasource.WordsLocalDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal abstract class DataSourceModule {
    @Binds
    abstract fun bindWordsLocalDataSource(wordsRoomDataSource: WordsRoomDataSource): WordsLocalDataSource
}
