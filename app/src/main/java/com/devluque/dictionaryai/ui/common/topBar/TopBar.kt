package com.devluque.dictionaryai.ui.common.topBar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.devluque.dictionaryai.ui.theme.getColorScheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    title: String,
    navigationBack: () -> Unit
) {
    TopAppBar(
        title = {
            Text(
                text = title,
                color = Color.White
            )
        },
        colors = TopAppBarDefaults.topAppBarColors().copy(
            containerColor = getColorScheme().primary
        ),
        navigationIcon = {
            IconButton(onClick = navigationBack) {
                Icon(
                    imageVector = Icons.AutoMirrored.Default.ArrowBack,
                    contentDescription = "",
                    tint = getColorScheme().onPrimary
                )
            }
        }
    )
}