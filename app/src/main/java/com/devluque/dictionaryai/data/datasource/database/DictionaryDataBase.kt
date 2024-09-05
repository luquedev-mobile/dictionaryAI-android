package com.devluque.dictionaryai.data.datasource.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.devluque.dictionaryai.data.model.Word

@Database(entities = [Word::class], version = 1, exportSchema = false)
abstract class DictionaryDataBase: RoomDatabase() {
    abstract val wordsDao: WordsDao
}