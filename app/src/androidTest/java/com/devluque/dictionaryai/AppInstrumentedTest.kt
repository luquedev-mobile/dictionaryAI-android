package com.devluque.dictionaryai

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.devluque.core.database.WordsDao
import com.devluque.dictionaryai.data.server.MockWebServerRule
import com.devluque.dictionaryai.data.server.fromJson
import com.devluque.search.ui.TEST_TAGS
import com.devluque.worddetail.ui.TESTS_TAGS.TITLE_TAG
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class AppInstrumentedTest {
    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val mockWebServerRule = MockWebServerRule()

    @get:Rule(order = 2)
    val androidComposeRule = createAndroidComposeRule<MainActivity>()

    @Inject
    lateinit var wordsDao: WordsDao

    @Before
    fun setUp() {
        mockWebServerRule.server.enqueue(
            MockResponse().fromJson("wordDetailResponseTest.json")
        )

        hiltRule.inject()
    }

    @Test
    fun click_search_word_navigates_to_wordDetail_and_word_save_in_db(): Unit = runTest {
        //GIVEN
        val wordToSearch = "tier"

        //WHEN
        androidComposeRule.onNodeWithTag(TEST_TAGS.BASIC_TEXT_FIELD_TAG).performTextInput(wordToSearch)
        androidComposeRule.onNodeWithTag(TEST_TAGS.SEARCH_BUTTON_TAG).performClick()

        //THEN
        androidComposeRule.onNodeWithTag(TITLE_TAG).assertTextContains("Significados de Tier")
        val recentWords = wordsDao.getRecentWords().first()
        assertEquals(wordToSearch, recentWords?.get(0)?.word)
    }

    @Test
    fun click_search_word_navigates_to_wordDetail_and_back_the_recent_word_is_appear(): Unit = runTest {
        //GIVEN
        val wordToSearch = "tier"

        //WHEN
        androidComposeRule.onNodeWithTag(TEST_TAGS.BASIC_TEXT_FIELD_TAG).performTextInput(wordToSearch)
        androidComposeRule.onNodeWithTag(TEST_TAGS.SEARCH_BUTTON_TAG).performClick()
        androidComposeRule.waitForIdle()
        androidComposeRule.activityRule.scenario.onActivity {
            it.onBackPressedDispatcher.onBackPressed()
        }

        //THEN
        androidComposeRule.onNodeWithText("Tier").assertIsDisplayed()
    }

    @Test
    fun show_all_recent_words_searched() = runTest {
        //GIVEN
        var wordToSearch = ""

        //WHEN
        (0..3).forEach {
            wordToSearch = "tier$it"
            androidComposeRule.onNodeWithTag(TEST_TAGS.BASIC_TEXT_FIELD_TAG).performTextInput(wordToSearch)
            androidComposeRule.onNodeWithTag(TEST_TAGS.SEARCH_BUTTON_TAG).performClick()
            androidComposeRule.waitForIdle()
            androidComposeRule.activityRule.scenario.onActivity {
                it.onBackPressedDispatcher.onBackPressed()
            }
        }

        //THEN
        (0..3).forEach {
            androidComposeRule.onNodeWithText("Tier$it").assertIsDisplayed()
            androidComposeRule.onNodeWithTag("${TEST_TAGS.LIST_WORDS_}Tier$it").assertIsDisplayed()
        }
    }
}