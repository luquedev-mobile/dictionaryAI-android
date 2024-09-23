package com.devluque.search.ui

import com.devluque.data.WordsRepository
import com.devluque.data.datasource.WordsLocalDataSource
import com.devluque.entities.Word
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

fun buildWordsRepository(
    localData: List<Word> = emptyList()
): WordsRepository {
    return WordsRepository(
        FakeWordsLocalDataSource().apply {
            inMemoryWords.value = localData
        }
    )
}

class FakeWordsLocalDataSource: WordsLocalDataSource{
    val inMemoryWords = MutableStateFlow<List<Word>>(emptyList())

    override val getRecentWords: Flow<List<Word>>
        get() = inMemoryWords

    override suspend fun insertWord(word: String) {
        inMemoryWords.value = inMemoryWords.first() + Word(word)
    }

    override fun searchWords(query: String): Flow<List<Word>> = inMemoryWords.map { it.filter { it.word.contains(query) } }

    override suspend fun deleteWord(word: String) {
        inMemoryWords.value = inMemoryWords.value.map { it.copy() }.filter { it.word != word }
    }
}