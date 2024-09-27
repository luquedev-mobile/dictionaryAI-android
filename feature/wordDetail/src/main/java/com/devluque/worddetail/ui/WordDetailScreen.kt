package com.devluque.worddetail.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.devluque.common.Screen
import com.devluque.common.speaker.Speaker
import com.devluque.domain.Result
import com.devluque.domain.SpeakerModer
import com.devluque.entities.Meaning
import com.devluque.entities.RemoteWordDetailRequest
import com.devluque.entities.WordDetailItem
import com.devluque.worddetail.ui.TESTS_TAGS.SWIPE_TAG
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

object TESTS_TAGS {
    const val SWIPE_TAG = "swipe"
}

@Composable
fun WordDetailScreen(
    word: String,
    viewModel: WordDetailViewModel
) {
    val state by viewModel.uiState.collectAsState()
    WordDetailScreen(
        word = word,
        event = viewModel.events,
        speak = { text, speakerModer ->
            viewModel.speak(text, speakerModer)
        },
        init = {
            viewModel.init(it)
        },
        refresh = {
            viewModel.refresh()
        },
        state = state
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WordDetailScreen(
    word: String = "",
    event: Flow<WordDetailViewModel.WordDetailEvent> = emptyFlow(),
    speak: (String, SpeakerModer) -> Unit,
    init: (RemoteWordDetailRequest) -> Unit,
    refresh: () -> Unit,
    fileName: String = "wordDetailRequest.json",
    state: Result<WordDetailItem>
) {
    val wordDetailState: WordDetailState = rememberWordDetailState(
        events = event
    )

    wordDetailState.TextToSpeechEvent()

    wordDetailState.GetWordDetailRequest(word, fileName = fileName) { wordDetailRequest ->
        wordDetailRequest?.let {
            wordDetailState.lastRequest.value = {
                init(it)
            }.also {
                it.invoke()
            }
        }
    }

    Screen(
        state = state,
        onClickRetry = { wordDetailState.lastRequest.value.invoke() }
    ) { data ->
        PullToRefreshBox(
            isRefreshing = false,
            onRefresh = {
                wordDetailState.lastRequest.value = {
                    refresh()
                }.also {
                    it.invoke()
                }
            },
            modifier = Modifier
                .testTag(SWIPE_TAG)
        ) {
            LazyColumn(
                modifier = Modifier
                    .padding(24.dp)
                    //.testTag(SWIPE_TAG)
            ) {
                item {
                    Text(
                        text = wordDetailState.getTitleAnnotatedString(
                            data.word
                        ),
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Speaker { mode ->
                        speak(data.word, mode)
                    }
                    Spacer(modifier = Modifier.height(24.dp))
                }
                items(data.meanings?.size ?: 0) { index ->
                    data.meanings?.get(index)?.let {
                        Item(
                            index + 1,
                            meaning = it,
                            wordDetailState = wordDetailState,
                            speaker = { mode ->
                                it.exampleEnglish?.let { speakExampleEnglish ->
                                    speak(speakExampleEnglish, mode)
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun Item(
    index: Int,
    meaning: Meaning,
    wordDetailState: WordDetailState,
    speaker: (SpeakerModer) -> Unit
) {
    Row {
        SelectionContainer {
            Text(
                text = wordDetailState.getMeaningAnnotatedString(meaning, index),
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight(500)
                ),
                modifier = Modifier.weight(1f),
            )
        }

        Spacer(modifier = Modifier.width(12.dp))
        // TODO: Save word
//        Icon(
//            painter = painterResource(id = R.drawable.ic_save_word),
//            contentDescription = "",
//            tint = Color.Black
//        )
    }
    SelectionContainer {
        Text(
            text = wordDetailState.getExampleAnnotatedString(meaning),
            modifier = Modifier
                .padding(vertical = 16.dp)
                .padding(start = 16.dp),
        )
    }
    Speaker {
        speaker(it)
    }
    Spacer(modifier = Modifier.height(24.dp))
}