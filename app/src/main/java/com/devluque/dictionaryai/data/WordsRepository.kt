package com.devluque.dictionaryai.data

import com.devluque.dictionaryai.data.datasource.WordsLocalDataSource
import com.devluque.dictionaryai.data.model.Word
import kotlinx.coroutines.flow.Flow

class WordsRepository(
    private val localDataSource: WordsLocalDataSource
) {
    suspend fun insertWord(word: Word) = localDataSource.insertWord(word)
    fun searchWords(query: String): Flow<List<Word>> = localDataSource.searchWords(query)
    val getRecentWords: Flow<List<Word>> = localDataSource.getRecentWords
    suspend fun deleteWord(word: String) = localDataSource.deleteWord(word)
}