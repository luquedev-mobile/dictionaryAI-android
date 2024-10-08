package com.devluque.data

import com.devluque.data.datasource.WordsLocalDataSource
import com.devluque.entities.Word
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WordsRepository @Inject constructor(
    private val localDataSource: WordsLocalDataSource
) {
    suspend fun insertWord(word: String) = localDataSource.insertWord(word)
    fun searchWords(query: String): Flow<List<Word>> = localDataSource.searchWords(query)
    val getRecentWords: Flow<List<Word>>
        get() = localDataSource.getRecentWords
    suspend fun deleteWord(word: String) = localDataSource.deleteWord(word)
}