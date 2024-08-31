package com.devluque.dictionaryai.ui.search

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

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