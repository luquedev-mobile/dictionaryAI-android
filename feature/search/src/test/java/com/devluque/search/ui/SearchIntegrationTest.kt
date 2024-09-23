package com.devluque.search.ui

import app.cash.turbine.test
import com.devluque.entities.Word
import com.devluque.sampleWords
import com.devluque.testrules.CoroutinesTestRule
import com.devluque.usecases.DeleteWordUseCase
import com.devluque.usecases.GetRecentWordsUseCase
import com.devluque.usecases.InsertWordUseCase
import com.devluque.usecases.SearchWordsUseCase
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class SearchIntegrationTest {
    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    private lateinit var vm : SearchViewModel

    private val localData: List<Word> = sampleWords("play", "play ground", "word4")

    @Before
    fun setUp() {
        val wordsRepository = buildWordsRepository(localData)
        vm = SearchViewModel(
            searchWordsUseCase = SearchWordsUseCase(wordsRepository),
            getRecentWordsUseCase = GetRecentWordsUseCase(wordsRepository),
            insertWordUseCase = InsertWordUseCase(wordsRepository),
            deleteWordUseCase = DeleteWordUseCase(wordsRepository)
        )
    }

    @Test
    fun `when searched is not empty, then uiState will have the coincidences that exist in recent words `() =
        runTest {
            //GIVEN
            val wordToSearch = "pl"

            //WHEN
            vm.searchWords(wordToSearch)

            //THEN
            vm.uiState.test {
                assertEquals(emptyList<Word>(), awaitItem())
                assertEquals(localData.filter { it.word.contains(wordToSearch) }, awaitItem())
            }
        }

    @Test
    fun `when searched is empty, then uiState will have recent words `() = runTest {
        //GIVEN
        val wordToSearch = "pl"

        vm.searchWords(wordToSearch)

        vm.uiState.test {
            assertEquals(emptyList<Word>(), awaitItem())
            assertEquals(localData.filter { it.word.contains(wordToSearch) }, awaitItem())

            //WHEN
            vm.searchWords("")

            //THEN
            assertEquals(localData, awaitItem())
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `when insertWord is called, then uiState will have new word in recent words`() = runTest {
        vm.uiState.test {
            //GIVEN
            val wordToInsert = "newWord"
            assertEquals(emptyList<Word>(), awaitItem())

            //WHEN
            vm.insertWord(wordToInsert)
            runCurrent()

            //THEN
            assertEquals(localData + Word(wordToInsert), awaitItem())
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `when deleteWord is called, then uiState will not have word in recent words`() = runTest {
        vm.uiState.test {
            //GIVEN
            val wordToDeleted = "play"
            assertEquals(emptyList<Word>(), awaitItem())

            //WHEN
            vm.deleteWord(wordToDeleted)
            runCurrent()

            //THEN
            assertEquals(localData.filter { it.word != wordToDeleted }, awaitItem())
        }
    }
}

