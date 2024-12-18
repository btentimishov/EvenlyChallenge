package com.baktyiar.evenlychallenge.presentation.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White


private val LightColorScheme = lightColorScheme(
    primary = Orange,
    onPrimary = White,
    secondary = Color(0xFF6D6D6D),
    onSecondary = White,
    tertiary = Pink40,
    onTertiary = White,
    background = White,
    onBackground = Color.Black,
    surface = White,
    onSurface = Color.Black
)

@Composable
fun EvenlyTheme (
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = Typography,
        content = content
    )
}