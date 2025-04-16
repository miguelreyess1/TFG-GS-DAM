package com.example.myfinance.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColors = lightColorScheme(
    primary = androidx.compose.ui.graphics.Color(0xFF6200EE),
    onPrimary = androidx.compose.ui.graphics.Color.White,
    secondary = androidx.compose.ui.graphics.Color(0xFF03DAC6),
    onSecondary = androidx.compose.ui.graphics.Color.Black
)

@Composable
fun MyFinanceTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = LightColors,
        typography = androidx.compose.material3.Typography(), // Puedes personalizar la tipografía
        content = content
    )
}
