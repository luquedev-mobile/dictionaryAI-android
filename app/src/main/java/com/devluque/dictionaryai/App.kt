package com.devluque.dictionaryai

import android.app.Application
import androidx.room.Room
import com.devluque.dictionaryai.framework.database.DictionaryDataBase

class App: Application() {
    lateinit var db: DictionaryDataBase
        private set

    override fun onCreate() {
        super.onCreate()
        db = Room.databaseBuilder(
            applicationContext,
            DictionaryDataBase::class.java,
            "dictionary-db"
        ).build()
    }
}