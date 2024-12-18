package com.devluque.common.loading

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import com.devluque.common.TESTS_TAGS

@Composable
fun Loading() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .testTag(TESTS_TAGS.LOADING),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}