package com.devluque.dictionaryai

import com.devluque.core.database.DbWord
import com.devluque.core.database.WordsDao
import com.devluque.data.WordsRepository
import com.devluque.data.datasource.AiRemoteDataSource
import com.devluque.dictionaryai.data.server.MockWebServerRule
import com.devluque.dictionaryai.data.server.fromJson
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class CheckHiltDBInstrumentedTest {
    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val mockWebServerRule = MockWebServerRule()

    @Inject
    lateinit var wordsRepository: WordsRepository

    @Inject
    lateinit var wordsDao: WordsDao

    @Before
    fun setUp() {
        mockWebServerRule.server.enqueue(
            MockResponse().fromJson("wordDetailResponseTest.json")
        )

        hiltRule.inject()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun test_toCheckHilt_works() {
        runTest {
            val recentWords = wordsRepository.getRecentWords.first()
            runCurrent()
            assertEquals(0, recentWords.size)
        }
    }

    @Test
    fun check_2_items_db() = runTest {
        val listOfWords = buildDatabaseMovies(1, 2)
        listOfWords.forEach {
            wordsDao.insertWord(it)
        }
        val recentWords = wordsDao.getRecentWords().first()
        assertEquals(2, recentWords?.size)
    }

    @Test
    fun check_6_items_db() = runTest {
        val listOfWords = buildDatabaseMovies(4, 5, 6, 3)
        listOfWords.forEach {
            wordsDao.insertWord(it)
        }

        val recentWords = wordsDao.getRecentWords().first()

        assertEquals(4, recentWords?.size)
    }

    @Test
    fun words_not_repeat_in_database() = runTest {
        val listOfWords = buildDatabaseMovies(4, 4, 4, 4)
        listOfWords.forEach {
            wordsDao.insertWord(it)
        }

        val recentWords = wordsDao.getRecentWords().first()

        assertEquals(1, recentWords?.size)
    }
}

private fun buildDatabaseMovies(vararg ids: Int): List<DbWord> = ids.map {
    DbWord(
        word = "word ${it}"
    )
}