package com.devluque.dictionaryai.data.datasource

import com.devluque.dictionaryai.data.model.Word
import com.devluque.dictionaryai.data.datasource.database.WordsDao
import kotlinx.coroutines.flow.filterNotNull

class WordsLocalDataSource(private val wordsDao: WordsDao) {
    suspend fun insertWord(word: Word) = wordsDao.insertWord(word)
    fun searchWords(query: String) = wordsDao.searchWords(query).filterNotNull()
    val getRecentWords = wordsDao.getRecentWords().filterNotNull()
    suspend fun deleteWord(word: String) = wordsDao.deleteWord(word)
}