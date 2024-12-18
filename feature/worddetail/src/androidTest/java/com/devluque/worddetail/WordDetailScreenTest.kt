package com.devluque.worddetail

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.test.swipeDown
import com.devluque.common.TESTS_TAGS
import com.devluque.domain.Result
import com.devluque.entities.Meaning
import com.devluque.entities.WordDetailItem
import com.devluque.worddetail.ui.TESTS_TAGS.SWIPE_TAG
import com.devluque.worddetail.ui.WordDetailScreen
import junit.framework.TestCase.assertEquals
import org.junit.Rule
import org.junit.Test

class WordDetailScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun whenNameFileIsNotCorrect_thenShowError() {
        composeTestRule.setContent {
            WordDetailScreen(
                speak = { _, _ -> },
                init = {},
                refresh = { /*TODO*/ },
                state = Result.Idle,
                fileName = "wordDetailRequest"
            )
        }

        composeTestRule.onNodeWithTag(TESTS_TAGS.ERROR_SCREEN).assertExists()
    }

    @Test
    fun whenNameFileIsCorrect_thenInitIsCalled() {
        var initCalled = false
        composeTestRule.setContent {
            WordDetailScreen(
                speak = { _, _ -> },
                init = {
                    initCalled = true
                },
                refresh = { /*TODO*/ },
                state = Result.Loading
            )
        }

        composeTestRule.onNodeWithTag(TESTS_TAGS.LOADING).assertIsDisplayed()
        assert(initCalled)
    }

    @Test
    fun whenErrorState_thenShowError() {
        composeTestRule.setContent {
            WordDetailScreen(
                speak = { _, _ -> },
                init = {},
                refresh = { /*TODO*/ },
                state = Result.Error(Exception())
            )
        }

        composeTestRule.onNodeWithText("No se cargó la información").assertExists()
        composeTestRule.onNodeWithTag(TESTS_TAGS.ERROR_SCREEN).assertExists()
    }

    @Test
    fun whenErrorStateFirstTimeAndTryAgain_thenInitIsCalled() {
        //GIVEN
        var attemptsToTry = 0
        composeTestRule.setContent {
            WordDetailScreen(
                speak = { _, _ -> },
                init = {
                    attemptsToTry++
                },
                refresh = { /*TODO*/ },
                state = Result.Error(Exception())
            )
        }

        //WHEN
        composeTestRule.onNodeWithTag(TESTS_TAGS.BUTTON_TRY_AGAIN).performClick()

        //THEN
        assertEquals(2, attemptsToTry)
    }

    @Test
    fun whenSwipeToRefresh_thenRefreshIsCalled() {
        //GIVEN
        var refreshCalled = false
        composeTestRule.setContent {
            WordDetailScreen(
                speak = { _, _ -> },
                init = {},
                refresh = {
                    refreshCalled = true
                },
                state = Result.Success(
                    WordDetailItem(
                        word = "word",
                        meanings = listOf(
                            Meaning(
                                explanation = "explanation",
                                mean = "mean"
                            ),
                            Meaning(
                                explanation = "explanation",
                                mean = "mean"
                            )
                        )
                    )
                )
            )
        }

        //WHEN
        composeTestRule.onNodeWithTag(SWIPE_TAG).assertIsDisplayed()
        composeTestRule.onNodeWithTag(SWIPE_TAG).performTouchInput {
            swipeDown()
        }
        composeTestRule.waitForIdle()

        //THEN
        assert(refreshCalled)
    }
}