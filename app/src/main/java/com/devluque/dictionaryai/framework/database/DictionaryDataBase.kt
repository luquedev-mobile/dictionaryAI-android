package com.devluque.dictionaryai.framework.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [DbWord::class], version = 1, exportSchema = false)
abstract class DictionaryDataBase: RoomDatabase() {
    abstract val wordsDao: WordsDao
}