package com.devluque.dictionaryai.ui

import androidx.compose.runtime.Composable
import com.devluque.dictionaryai.Result
import com.devluque.dictionaryai.ui.common.error.ErrorScreen
import com.devluque.dictionaryai.ui.common.loading.Loading

@Composable
fun <T> Screen(
    state: Result<T>,
    onClickRetry: () -> Unit,
    content: @Composable (T) -> Unit
) {
    when(state) {
        is Result.Error -> {
            ErrorScreen(
                onClickRetry = onClickRetry
            )
        }
        Result.Loading -> {
            Loading()
        }
        is Result.Success -> {
            content(state.data)
        }
    }
}