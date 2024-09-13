package com.devluque.dictionaryai.ui.wordDetail

import android.speech.tts.TextToSpeech
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.devluque.domain.Meaning
import com.devluque.domain.remote.common.Content
import com.devluque.domain.remote.common.Part
import com.devluque.domain.remote.worddetail.RemoteWordDetailRequest
import com.devluque.dictionaryai.ui.common.readFile.ManagerJsonFile
import com.devluque.dictionaryai.ui.theme.getColorScheme
import kotlinx.coroutines.flow.Flow
import java.util.Locale

class WordDetailState(
    var lastRequest: MutableState<() -> Unit>,
    val events: Flow<WordDetailViewModel.WordDetailEvent>
) {
    @Composable
    fun GetWordDetailRequest(
        word: String,
        getContent: (RemoteWordDetailRequest?) -> Unit
    ) {
        ManagerJsonFile<RemoteWordDetailRequest>(
            getContent = { wordDetailRequest ->
                wordDetailRequest?.contents = mutableListOf(
                    Content(
                        parts = listOf(
                            Part(
                                text = "significados de '$word'",
                            )
                        ),
                        role = "user"
                    )
                )

                getContent(wordDetailRequest)
            },
            fileName = "wordDetailRequest.json"
        )
    }

    @Composable
    fun TextToSpeechEvent() {
        val context = LocalContext.current
        val textToSpeech = remember {
            TextToSpeech(context, null)
        }

        val lifecycleOwner = androidx.lifecycle.compose.LocalLifecycleOwner.current
        LaunchedEffect(lifecycleOwner) {
            lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                events.collect { event ->
                    when (event) {
                        is WordDetailViewModel.WordDetailEvent.Speak -> {
                            textToSpeech.setSpeechRate(event.speakerModer.speechRate)
                            @Suppress("DEPRECATION")
                            if (textToSpeech.language.isO3Language != Locale.UK.isO3Language) {
                                textToSpeech.language = Locale.UK
                            }
                            textToSpeech.speak(event.text, TextToSpeech.QUEUE_FLUSH, null, null)
                        }
                    }
                }
            }
        }
    }

    fun getTitleAnnotatedString(word: String) = buildAnnotatedString {
        withStyle(
            SpanStyle(
                fontSize = 24.sp
            )
        ) {
            append("Significados de ")
        }
        withStyle(
            SpanStyle(
                fontSize = 24.sp,
                fontWeight = FontWeight(500)
            )
        ){
            append(word)
        }
    }

    @Composable
    fun getMeaningAnnotatedString(meaning: Meaning, index: Int) = buildAnnotatedString {
        meaning.partOfSpeech?.let {
            append("${index}. ${meaning.partOfSpeech} ")
        }?: run {
            append("${index}. ")
        }
        withStyle(
            SpanStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight(1000),
                color = getColorScheme().primary
            )
        ) {
            append(meaning.mean)
        }
        meaning.explanation?.let {
            append(": ${meaning.explanation}")
        }
    }

    fun getExampleAnnotatedString(meaning: Meaning) = buildAnnotatedString {
        meaning.exampleEnglish?.let {
            withStyle(
                SpanStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight(500)
                )
            ) {
                append("· ${meaning.exampleEnglish}")
            }
        }
        meaning.exampleSpanish?.let {
            append(" (${meaning.exampleSpanish})")
        }
    }
}

@Composable
fun rememberWordDetailState(
    lazyGridState: LazyGridState = rememberLazyGridState(),
    lastRequest: MutableState<() -> Unit> = remember { mutableStateOf({}) },
    events: Flow<WordDetailViewModel.WordDetailEvent>
): WordDetailState {
    return remember(lazyGridState) {
        WordDetailState(lastRequest, events)
    }
}