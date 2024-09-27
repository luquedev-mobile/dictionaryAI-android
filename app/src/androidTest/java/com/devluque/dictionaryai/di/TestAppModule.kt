package com.devluque.dictionaryai.di

import android.app.Application
import androidx.room.Room
import com.devluque.core.FrameworkCoreExtrasModule
import com.devluque.core.database.DictionaryDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.asExecutor
import javax.inject.Named
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [FrameworkCoreExtrasModule::class]
)
object TestAppModule {

    @Provides
    @Singleton
    fun provideDatabase(app: Application): DictionaryDataBase {
        val db = Room.inMemoryDatabaseBuilder(
            app,
            DictionaryDataBase::class.java
        )
            .setTransactionExecutor(Dispatchers.Main.asExecutor())
            .allowMainThreadQueries()
            .build()
        return db
    }

    @Provides
    @Singleton
    @Named("apiUrl")
    fun provideApiUrl(): String = "http://localhost:8080"

}