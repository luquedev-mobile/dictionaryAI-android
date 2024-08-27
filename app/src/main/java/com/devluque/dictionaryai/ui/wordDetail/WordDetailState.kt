package com.devluque.dictionaryai.ui.wordDetail

import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp
import com.devluque.dictionaryai.data.common.Content
import com.devluque.dictionaryai.data.common.Part
import com.devluque.dictionaryai.data.wordDetail.Meaning
import com.devluque.dictionaryai.data.wordDetail.WordDetailRequest
import com.devluque.dictionaryai.ui.common.readFile.ManagerJsonFile
import com.devluque.dictionaryai.ui.theme.getColorScheme

class WordDetailState(
    var lastRequest: MutableState<() -> Unit>
) {
    @Composable
    fun GetWordDetailRequest(
        word: String,
        getContent: (WordDetailRequest?) -> Unit
    ) {
        ManagerJsonFile<WordDetailRequest>(
            getContent = { wordDetailRequest ->
                wordDetailRequest?.contents = mutableListOf(
                    Content(
                        parts = listOf(
                            Part(
                                text = "principales significados de la palabra '$word', siempre incluye los campos 'explanation', 'mean', 'example_english' y 'example_spanish'",
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
        meaning.type?.let {
            append("${index}. ${meaning.type} ")
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
        meaning.example_english?.let {
            withStyle(
                SpanStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight(500)
                )
            ) {
                append("· ${meaning.example_english}")
            }
        }
        meaning.example_spanish?.let {
            append(" (${meaning.example_spanish})")
        }
    }
}

@Composable
fun rememberWordDetailState(
    lazyGridState: LazyGridState = rememberLazyGridState(),
    lastRequest: MutableState<() -> Unit> = remember { mutableStateOf({}) }
): WordDetailState {
    return remember(lazyGridState) {
        WordDetailState(lastRequest)
    }
}