package com.devluque.dictionaryai.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFFBB86FC),           // Lila suave
    primaryContainer = Color(0xFF3700B3),  // Lila profundo
    secondary = Color(0xFF03DAC6),         // Aqua suave
    secondaryContainer = Color(0xFF018786),// Aqua oscuro
    tertiary = Color(0xFFCF6679),          // Rosa desaturado
    tertiaryContainer = Color(0xFFB00020), // Rojo oscuro
    background = Color(0xFF121212),        // Negro suave
    surface = Color(0xFF1E1E1E),           // Gris oscuro
    surfaceVariant = Color(0xFF2C2C2C),    // Gris ligeramente más claro
    error = Color(0xFFCF6679),             // Rosa desaturado
    onPrimary = Color(0xFFFFFFFF),         // Negro sobre primario
    onSecondary = Color(0xFF000000),       // Negro sobre secundario
    onTertiary = Color(0xFF000000),        // Negro sobre terciario
    onBackground = Color(0xFFE0E0E0),      // Gris claro sobre fondo
    onSurface = Color(0xFFE0E0E0),         // Gris claro sobre superficie
    onSurfaceVariant = Color(0xFF757575),  // Gris medio
    inverseSurface = Color(0xFFE0E0E0),    // Gris claro invertido
    inverseOnSurface = Color(0xFF121212),  // Negro invertido
    inversePrimary = Color(0xFF3700B3),    // Lila profundo invertido
    outline = Color(0xFF888888),           // Gris claro para delineado
    onError = Color(0xFF000000)            // Negro sobre error
)

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF37474F),           // Gris azulado oscuro
    primaryContainer = Color(0xFF62727B),   // Gris azulado claro
    secondary = Color(0xFF8D6E63),          // Marrón suave
    secondaryContainer = Color(0xFFBCAAA4), // Marrón claro
    tertiary = Color(0xFF607D8B),           // Gris azulado intermedio
    tertiaryContainer = Color(0xFFC1D5E0),  // Gris azulado muy claro
    background = Color(0xFFFAFAFA),         // Blanco casi puro
    surface = Color(0xFFFFFFFF),            // Blanco puro
    surfaceVariant = Color(0xFFE0E0E0),     // Gris claro
    error = Color(0xFFD32F2F),              // Rojo mate
    onPrimary = Color(0xFFFFFFFF),          // Blanco sobre primario
    onSecondary = Color(0xFFFFFFFF),        // Blanco sobre secundario
    onTertiary = Color(0xFFFFFFFF),         // Blanco sobre terciario
    onBackground = Color(0xFF212121),       // Gris oscuro sobre fondo
    onSurface = Color(0xFF212121),          // Gris oscuro sobre superficie
    onSurfaceVariant = Color(0xFF757575),   // Gris medio
    inverseSurface = Color(0xFF212121),     // Gris oscuro invertido
    inverseOnSurface = Color(0xFFF5F5F5),   // Gris claro invertido
    inversePrimary = Color(0xFF90A4AE),     // Gris azulado claro invertido
    outline = Color(0xFFB0BEC5),            // Gris claro para delineado
    onError = Color(0xFFFFFFFF)             // Blanco sobre error
)

@Composable
fun getColorScheme(): ColorScheme {
    return if (isSystemInDarkTheme()) {
        DarkColorScheme
    } else {
        LightColorScheme
    }
}

@Composable
fun DictionaryAITheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}