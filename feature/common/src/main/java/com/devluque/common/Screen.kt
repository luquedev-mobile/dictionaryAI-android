package com.devluque.common

import androidx.compose.runtime.Composable
import com.devluque.common.error.ErrorScreen
import com.devluque.common.loading.Loading
import com.devluque.domain.Result

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

        Result.Idle -> {}
    }
}