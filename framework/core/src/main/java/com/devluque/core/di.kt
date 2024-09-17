package com.devluque.core

import android.content.Context
import androidx.room.Room
import com.devluque.core.database.DictionaryDataBase
import com.devluque.core.network.AiClient
import org.koin.core.module.dsl.factoryOf
import org.koin.core.qualifier.named
import org.koin.dsl.module

val diFrameworkCoreModule = module {
    single {
        Room.databaseBuilder(
            get(),
            DictionaryDataBase::class.java,
            "dictionary-db"
        ).build()
    }

    factory { get<DictionaryDataBase>().wordsDao() }
}