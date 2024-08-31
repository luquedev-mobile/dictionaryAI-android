package com.devluque.dictionaryai.ui.wordDetail

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.devluque.dictionaryai.R
import com.devluque.dictionaryai.data.wordDetail.Meaning
import com.devluque.dictionaryai.ui.common.error.ErrorScreen
import com.devluque.dictionaryai.ui.common.loading.Loading

@Composable
fun WordDetail(
    word: String,
    viewModel: WordDetailViewModel
) {
    val state by viewModel.state.collectAsState()
    val wordDetailState: WordDetailState = rememberWordDetailState()

    wordDetailState.GetWordDetailRequest(word) { wordDetailRequest ->
        wordDetailRequest?.let {
            wordDetailState.lastRequest.value = {
                viewModel.init(it)
            }.also {
                it.invoke()
            }
        }
    }

    if (state.loading) {
        Loading()
    } else if (state.error) {
        ErrorScreen {
            wordDetailState.lastRequest.value.invoke()
        }
    } else if (state.wordDetail != null) {
        LazyColumn(
            modifier = Modifier
                .padding(24.dp)
        ) {
            item {
                Row {
                    Text(
                        text = wordDetailState.getTitleAnnotatedString(
                            state.wordDetail?.word ?: ""
                        ),
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .weight(1f)
                    )
                    Icon(
                        painter = painterResource(id = R.drawable.ic_refresh),
                        contentDescription = "",
                        modifier = Modifier
                            .clickable { viewModel.refresh() }
                    )
                }
                Spacer(modifier = Modifier.height(24.dp))
            }
            items(state.wordDetail?.meanings?.size ?: 0) { index ->
                state.wordDetail?.meanings?.get(index)?.let {
                    Item(
                        index + 1,
                        meaning = it,
                        wordDetailState = wordDetailState
                    )
                }
            }
        }
    }
}

@Composable
private fun Item(
    index: Int,
    meaning: Meaning,
    wordDetailState: WordDetailState
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
}