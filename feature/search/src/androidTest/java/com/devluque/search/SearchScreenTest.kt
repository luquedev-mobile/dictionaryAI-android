package com.devluque.search

import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.devluque.sampleWords
import com.devluque.search.ui.SearchScreen
import com.devluque.search.ui.TEST_TAGS
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

class SearchScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun whenBasicTextFieldIsEmpty_ThenSearchButtonIsDisabled() {
        composeTestRule.setContent {
            SearchScreen(
                onSearchMeaningOfWord = {},
                searchWords = {},
                onDelete = {},
                words = emptyList()
            )
        }

        composeTestRule.onNodeWithTag(TEST_TAGS.BASIC_TEXT_FIELD_TAG).assertTextContains("")
        composeTestRule.onNodeWithTag(TEST_TAGS.SEARCH_BUTTON_TAG).assertIsNotEnabled()
    }

    @Test
    fun whenBasicTextFieldIsNotEmpty_ThenSearchButtonIsEnabled() {
        //GIVEN
        val textToSearch = "down"
        composeTestRule.setContent {
            SearchScreen(
                onSearchMeaningOfWord = {},
                searchWords = {},
                onDelete = {},
                words = emptyList()
            )
        }

        //WHEN
        composeTestRule.onNodeWithTag(TEST_TAGS.BASIC_TEXT_FIELD_TAG).performTextInput(textToSearch)

        //THEN
        composeTestRule.onNodeWithTag(TEST_TAGS.BASIC_TEXT_FIELD_TAG)
            .assertTextContains(textToSearch)
        composeTestRule.onNodeWithTag(TEST_TAGS.SEARCH_BUTTON_TAG).assertIsEnabled()
    }

    @Test
    fun whenRecentWordsIsNotEmpty_thenShowListRecentWords() {
        //GIVEN
        val listRecentWords = sampleWords("word1", "word2", "word3", "word4")
        composeTestRule.setContent {
            SearchScreen(
                onSearchMeaningOfWord = {},
                searchWords = {},
                onDelete = {},
                words = listRecentWords
            )
        }

        listRecentWords.forEach { word ->
            composeTestRule.onNodeWithText(word.word).assertExists()
        }
    }

    @Test
    fun whenOnDeleteIsClick_thenListenerIsCalled() {
        //GIVEN
        var clickedWord = ""
        val listRecentWords = sampleWords("word1", "word2", "word3", "word4")
        composeTestRule.setContent {
            SearchScreen(
                onSearchMeaningOfWord = {},
                searchWords = {},
                onDelete = { word ->
                    clickedWord = word
                },
                words = listRecentWords
            )
        }

        //WHEN
        composeTestRule.onNodeWithTag("${TEST_TAGS.BUTTON_DELETE_TAG}${listRecentWords[0].word}")
            .performClick()

        //THEN
        assertEquals(listRecentWords[0].word, clickedWord)
    }

    @Test
    fun whenOnSearchMeaningOfWordIsClick_thenListenerIsCalled() {
        //GIVEN
        var clickedWord = ""
        val listRecentWords = sampleWords("word1", "word2", "word3", "word4")
        composeTestRule.setContent {
            SearchScreen(
                onSearchMeaningOfWord = {
                    clickedWord = it
                },
                searchWords = {},
                onDelete = { },
                words = listRecentWords
            )
        }

        //WHEN
        composeTestRule.onNodeWithText(listRecentWords[0].word).performClick()

        //THEN
        assertEquals(listRecentWords[0].word, clickedWord)
    }

    @Test
    fun whenWordIsSearchInBasicTextField_thenListenerIsCalled() = runTest {
        //GIVEN
        var clickedWord = ""
        val textToSearch = "down"
        val listRecentWords = sampleWords("word1", "word2", "word3", "word4")
        composeTestRule.setContent {
            SearchScreen(
                onSearchMeaningOfWord = {},
                searchWords = {
                    clickedWord = it
                },
                onDelete = { },
                words = listRecentWords
            )
        }

        //WHEN
        composeTestRule.onNodeWithTag(TEST_TAGS.BASIC_TEXT_FIELD_TAG).performTextInput(textToSearch)
        composeTestRule.awaitIdle()

        //THEN
        assertEquals(textToSearch, clickedWord)
    }
}