package com.devluque.core

import android.app.Application
import androidx.room.Room
import com.devluque.core.database.DictionaryDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FrameworkCoreModule {

    @Provides
    fun provideWordsDao(db: DictionaryDataBase) = db.wordsDao()

}

@Module
@InstallIn(SingletonComponent::class)
object FrameworkCoreExtrasModule {

    @Provides
    @Singleton
    fun provideDatabase(app: Application) = Room.databaseBuilder(
        app,
        DictionaryDataBase::class.java,
        "dictionary.db"
    ).build()

    @Provides
    @Singleton
    @Named("apiUrl")
    fun provideApiUrl(): String = "https://generativelanguage.googleapis.com/v1beta/"

}