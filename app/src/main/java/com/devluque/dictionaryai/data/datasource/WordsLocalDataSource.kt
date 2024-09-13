package com.devluque.dictionaryai.data.datasource

import com.devluque.dictionaryai.domain.Word
import kotlinx.coroutines.flow.Flow

interface WordsLocalDataSource {
    val getRecentWords: Flow<List<Word>>
    suspend fun insertWord(word: String)
    fun searchWords(query: String): Flow<List<Word>>

    suspend fun deleteWord(word: String)
}