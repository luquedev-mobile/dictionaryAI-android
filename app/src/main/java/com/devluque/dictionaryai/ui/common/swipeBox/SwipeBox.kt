package com.devluque.dictionaryai.ui.common.swipeBox

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun SwipeBox(
    swipeDone: () -> Unit,
    content: @Composable () -> Unit,
) {
    val swipeState = rememberSwipeState()

    swipeState.OnSwipeDone {
        swipeDone()
    }

    SwipeToDismissBox(
        state = swipeState.dismissState,
        backgroundContent = {
            val color = when (swipeState.dismissState.dismissDirection) {
                SwipeToDismissBoxValue.EndToStart -> {
                    Color.Red
                }

                else -> {
                    Color.Transparent
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color)
                    .padding(horizontal = 20.dp),
                contentAlignment = Alignment.CenterEnd
            ) {
                Text("Delete", color = Color.White)
            }
        },
        enableDismissFromStartToEnd = false
    ) {
        content()
    }
}