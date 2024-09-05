package com.devluque.dictionaryai.ui.common.swipeBox

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material3.SwipeToDismissBoxState
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class SwipeState(
    val dismissState: SwipeToDismissBoxState,
    private val scope: CoroutineScope
) {
    @Composable
    fun OnSwipeDone(
        onSwipeDone: () -> Unit
    ) {
        LaunchedEffect(dismissState.currentValue) {
            if (dismissState.currentValue == SwipeToDismissBoxValue.EndToStart) {
                scope.launch {
                    dismissState.reset()
                }
                onSwipeDone()
            }
        }
    }
}

@Composable
fun rememberSwipeState(
    dismissState: SwipeToDismissBoxState = rememberSwipeToDismissBoxState(),
    scope: CoroutineScope = rememberCoroutineScope()
): SwipeState {
    return remember(dismissState) {
        SwipeState(dismissState, scope)
    }
}