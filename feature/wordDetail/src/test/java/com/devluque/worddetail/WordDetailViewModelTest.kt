package com.devluque.worddetail

import app.cash.turbine.test
import com.devluque.domain.Content
import com.devluque.domain.ItemTypeString
import com.devluque.domain.Result
import com.devluque.domain.SafetySettings
import com.devluque.domain.SpeakerModer
import com.devluque.entities.Meanings
import com.devluque.entities.RemoteWordDetailRequest
import com.devluque.entities.WordDetailGenerationConfig
import com.devluque.entities.WordDetailItem
import com.devluque.entities.WordDetailItemsProperties
import com.devluque.entities.WordDetailMeaningProperties
import com.devluque.entities.WordDetailProperties
import com.devluque.entities.WordDetailResponseSchema
import com.devluque.testrules.CoroutinesTestRule
import com.devluque.usecases.FetchGenerateWordDetailUseCase
import com.devluque.worddetail.ui.WordDetailViewModel
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class WordDetailViewModelTest {
    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @Mock
    private lateinit var fetchGenerateWordDetailUseCase: FetchGenerateWordDetailUseCase
    @Mock
    private lateinit var remoteWordDetailRequest: RemoteWordDetailRequest
    @Mock
    private lateinit var wordDetailItem: WordDetailItem

    private lateinit var viewModel: WordDetailViewModel

    @Before
    fun setUp() {
        viewModel = WordDetailViewModel(fetchGenerateWordDetailUseCase)
    }

    @Test
    fun `when init is not called, then fetchGenerateWordDetailUseCase is not called`() = runTest {
        verify(fetchGenerateWordDetailUseCase, times(0)).invoke(remoteWordDetailRequest)
    }

    @Test
    fun `uiState emits loading and result after init`() = runTest {
        viewModel.uiState.test {
            //GIVEN
            whenever(fetchGenerateWordDetailUseCase(remoteWordDetailRequest)).thenReturn(flowOf(Result.Success(wordDetailItem)))
            assertEquals(Result.Idle, awaitItem())

            //WHEN
            viewModel.init(remoteWordDetailRequest)

            //THEN
            assertEquals(Result.Loading, awaitItem())
            verify(fetchGenerateWordDetailUseCase, times(1)).invoke(remoteWordDetailRequest)
            assertEquals(Result.Success(wordDetailItem), awaitItem())
        }
    }

    @Test
    fun `uiState emit error when fetchGenerateWordDetailUseCase fails`() = runTest {
        viewModel.uiState.test {
            //GIVEN
            val error = RuntimeException("boom")
            assertEquals(Result.Idle, awaitItem())
            whenever(fetchGenerateWordDetailUseCase(remoteWordDetailRequest)).thenReturn(flowOf(Result.Error(error)))

            //WHEN
            viewModel.init(remoteWordDetailRequest)

            //THEN
            assertEquals(Result.Loading, awaitItem())
            verify(fetchGenerateWordDetailUseCase, times(1)).invoke(remoteWordDetailRequest)
            assertEquals(Result.Error(error), awaitItem())
        }
    }

    @Test
    fun `refresh triggers getting data`() = runTest {
        viewModel.uiState.test {
            //GIVEN
            whenever(fetchGenerateWordDetailUseCase(remoteWordDetailRequest)).thenReturn(flowOf(Result.Success(wordDetailItem)))
            assertEquals(Result.Idle, awaitItem())
            viewModel.init(remoteWordDetailRequest)
            assertEquals(Result.Loading, awaitItem())
            verify(fetchGenerateWordDetailUseCase, times(1)).invoke(remoteWordDetailRequest)
            assertEquals(Result.Success(wordDetailItem), awaitItem())

            //WHEN
            viewModel.refresh()

            //THEN
            assertEquals(Result.Loading, awaitItem())
            verify(fetchGenerateWordDetailUseCase, times(2)).invoke(remoteWordDetailRequest)
            assertEquals(Result.Success(wordDetailItem), awaitItem())
        }
    }

    @Test
    fun `speak event is emitted correctly`() = runTest {
        viewModel.events.test {
            //GIVEN
            val text = "Hello"
            val speakerModer = SpeakerModer.Normal

            //WHEN
            viewModel.speak(text, speakerModer)

            //THEN
            val event = awaitItem()
            assert(event is WordDetailViewModel.WordDetailEvent.Speak)
            assert((event as WordDetailViewModel.WordDetailEvent.Speak).text == text)
            assert(event.speakerModer == speakerModer)
        }
    }
}