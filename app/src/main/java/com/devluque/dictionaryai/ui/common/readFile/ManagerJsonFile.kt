package com.devluque.dictionaryai.ui.common.readFile

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.devluque.dictionaryai.ui.common.error.ErrorScreen
import kotlinx.serialization.json.Json

@Composable
inline fun <reified T> ManagerJsonFile(
    fileName: String,
    crossinline getContent: (T?) -> Unit
) {
    val context = LocalContext.current
    var errorToLoadContent by remember{ mutableStateOf(false) }

    LaunchedEffect(Unit) {
        try {
            val jsonStringRequest = context.assets.open(fileName).bufferedReader()
                .use { it.readText() }

            val request =
                Json.decodeFromString<T>(jsonStringRequest)

            getContent(request)
        } catch (_: Exception) {
            errorToLoadContent = true
        }
    }

    if (errorToLoadContent) {
        ErrorScreen(
            subtitle = "Contacte con el administrador de la aplicación",
            mustShowRetryButton = false
        )
    }
}