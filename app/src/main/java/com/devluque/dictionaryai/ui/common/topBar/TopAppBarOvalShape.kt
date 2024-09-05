package com.devluque.dictionaryai.ui.common.topBar

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import com.devluque.dictionaryai.ui.theme.getColorScheme

@Composable
fun TopAppBarOvalShape(
    modifier: Modifier = Modifier,
    scrollOffset: Int = 0
) {
    val topBarState = rememberTopAppBarState(scrollOffset)

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(topBarState.animateTopBarHeight())
    ) {
        OvalShapeBackground(
            topBarState = topBarState
        )
        Text(
            text = "Dictionary AI",
            modifier = Modifier
                .align(Alignment.Center),
            style = TextStyle(
                fontSize = 24.sp,
                color = getColorScheme().onPrimary
            )
        )
    }
}

@Composable
fun OvalShapeBackground(
    modifier: Modifier = Modifier,
    topBarState: TopAppBarState
) {
    val animateCorner = topBarState.animateTopBarCorner()
    val color = getColorScheme().primary
    Canvas(
        modifier = modifier
            .fillMaxSize()
    ) {
        drawIntoCanvas {
            drawRoundRect(
                color = color,
                topLeft = Offset(0f, -size.height / 2f),
                cornerRadius = CornerRadius(size.width / animateCorner, size.height)
            )
        }
    }
}