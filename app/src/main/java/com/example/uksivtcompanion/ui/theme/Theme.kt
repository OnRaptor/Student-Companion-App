package com.example.uksivtcompanion.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.*
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import android.os.Build

private val DarkColorPalette = darkColorScheme(
    primary = Blue500,
    primaryContainer = Blue700,
    secondary = Teal200
)

private val LightColorPalette = lightColorScheme(
    primary = Blue200,
    primaryContainer = Blue500,
    secondary = Teal200

    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

@Composable
fun UksivtCompanionTheme(
    useDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {

    MaterialTheme(
        colorScheme = if (useDarkTheme) {
            DarkColorPalette
        } else {
            LightColorPalette
        },
        typography = Typography,
        content = content
    )
}