package com.devluque.dictionaryai.ui.search

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.devluque.dictionaryai.ui.wordDetail.WordDetailState

class SearchState(
    val interactionSource: MutableInteractionSource,
    val searchText: MutableState<String> = mutableStateOf("")
)

@Composable
fun rememberSearchState(
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
): SearchState {
    return remember(interactionSource) {
        SearchState(interactionSource)
    }
}