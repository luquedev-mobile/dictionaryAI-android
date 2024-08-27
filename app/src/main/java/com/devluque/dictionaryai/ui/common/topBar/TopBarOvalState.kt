package com.devluque.dictionaryai.ui.common.topBar

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

class TopAppBarState(
    private val scrollOffset: Int
) {
    private val maxTopBarHeight = 100.dp
    private val minTopBarHeight = 56.dp

    private val topBarHeight = maxTopBarHeight - (scrollOffset / 2).dp

    private val topBarHeightDp = maxOf(minTopBarHeight.value, minOf(maxTopBarHeight.value, topBarHeight.value))

    @Composable
    fun animateTopBarHeight(): Dp {
        val animateHeight by animateDpAsState(
            targetValue = if (topBarHeightDp == maxTopBarHeight.value) maxTopBarHeight else minTopBarHeight, label = ""
        )

        return animateHeight
    }

    @Composable
    fun animateTopBarCorner(): Float {
        val animateCorner by animateFloatAsState(
            targetValue = if (maxOf(1.0f / (1.0f + scrollOffset), 0.0f) == 1.0f) 1.0f else 0f, label = ""
        )

        return animateCorner
    }
}

@Composable
fun rememberTopAppBarState(
    scrollOffset: Int
): TopAppBarState {
    return remember(scrollOffset) { TopAppBarState(scrollOffset) }
}