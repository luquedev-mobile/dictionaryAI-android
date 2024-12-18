package com.devluque.data

import com.devluque.data.datasource.WordsLocalDataSource
import com.devluque.sampleWord
import com.devluque.sampleWords
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class WordsRepositoryTest {
    @Mock
    lateinit var localDataSource: WordsLocalDataSource

    private lateinit var repository: WordsRepository

    @Before
    fun setUp() {
        repository = WordsRepository(localDataSource)
    }

    @Test
    fun `Word is saved`() = runBlocking {
        val wordToSave = "word"

        repository.insertWord(wordToSave)

        verify(localDataSource).insertWord(wordToSave)
    }

    @Test
    fun `Word is deleted`() = runBlocking {
        val wordToDelete = "word"

        repository.deleteWord(wordToDelete)

        verify(localDataSource).deleteWord(wordToDelete)
    }

    @Test
    fun `Words are searched`() = runBlocking {
        val wordToSearch = sampleWord("word")
        val wordsSearched = flowOf(sampleWords("word", "word2"))
        whenever(localDataSource.searchWords(wordToSearch.word)).thenReturn(wordsSearched)

        val result = repository.searchWords(wordToSearch.word)

        assertEquals(wordToSearch, result.first()[0])
    }

    @Test
    fun `Recent words are retrieved`() = runBlocking {
        val recentWords = sampleWords("word", "word2")
        whenever(localDataSource.getRecentWords).thenReturn(flowOf(recentWords))

        val result = repository.getRecentWords

        assertEquals(recentWords, result.first())
    }
}