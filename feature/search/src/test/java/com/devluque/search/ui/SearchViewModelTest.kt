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
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class SearchViewModelTest {
    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @Mock
    lateinit var searchWordsUseCase: SearchWordsUseCase
    @Mock
    lateinit var getRecentWordsUseCase: GetRecentWordsUseCase
    @Mock
    lateinit var insertWordUseCase: InsertWordUseCase
    @Mock
    lateinit var deleteWordUseCase: DeleteWordUseCase

    private lateinit var vm : SearchViewModel

    private val recentWords = sampleWords("word", "word2", "word3")

    private val searchedWords = sampleWords("word4", "word5", "word6")

    @Before
    fun setUp() {
        whenever(getRecentWordsUseCase()).thenReturn(flowOf(recentWords))

        vm = SearchViewModel(
            searchWordsUseCase = searchWordsUseCase,
            getRecentWordsUseCase = getRecentWordsUseCase,
            insertWordUseCase = insertWordUseCase,
            deleteWordUseCase = deleteWordUseCase
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `when viewModel is initialized, then getRecentWordsUseCase is called ` () = runTest {
        vm.uiState.first()
        runCurrent()
        verify(getRecentWordsUseCase, times(1)).invoke()
    }

    @Test
    fun `when viewModel is initialized, then getRecentWordsUseCase is called and uiState will have recent words` () = runTest {
        vm.uiState.test {
            assertEquals(emptyList<Word>(), awaitItem())
            assertEquals(recentWords, awaitItem())
        }
    }

    @Test
    fun `when searchWord isEmpty, then uiState will have recent words`() = runTest {
        vm.uiState.test {
            //GIVEN
            assertEquals(emptyList<Word>(), awaitItem())
            assertEquals(recentWords, awaitItem())
            val wordToSearch = "word"
            whenever(searchWordsUseCase(wordToSearch)).thenReturn(flowOf(searchedWords))

            //WHEN
            vm.searchWords(wordToSearch)
            assertEquals(searchedWords, awaitItem())
            vm.searchWords("")

            //THEN
            assertEquals(recentWords, awaitItem())
        }
    }

    @Test
    fun `when searchWord isNotEmpty, then uiState will have searched words` () = runTest {
        vm.uiState.test {
            //GIVEN
            assertEquals(emptyList<Word>(), awaitItem())
            assertEquals(recentWords, awaitItem())
            val wordToSearch = "word"
            whenever(searchWordsUseCase(wordToSearch)).thenReturn(flowOf(searchedWords))

            //WHEN
            vm.searchWords(wordToSearch)

            //THEN
            assertEquals(searchedWords, awaitItem())
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `when insertWord is called, then insertWordUseCase is called`() = runTest {
        //GIVEN
        val wordInsert = "word7"

        //WHEN
        vm.insertWord(wordInsert)
        advanceUntilIdle()

        //THEN
        verify(insertWordUseCase, times(1)).invoke(wordInsert)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `deleteWord should call deleteWordUseCase`() = runTest {
        //GIVEN
        val wordToDelete = "word8"

        //WHEN
        vm.deleteWord(wordToDelete)
        advanceUntilIdle()

        //THEN
        verify(deleteWordUseCase, times(1)).invoke(wordToDelete)
    }
}