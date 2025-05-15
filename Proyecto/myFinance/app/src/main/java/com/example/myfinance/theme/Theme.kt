package com.example.myfinance.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

private val colorPrincipal = Color(0xFF6ADDB0)
private val colorInactivo = Color(0xFF757575)
private val colorInactivoOscuro = Color(0xFF424242)

// Paleta de colores para modo claro
private val LightColors = lightColorScheme(
    primary = colorPrincipal,
    onPrimary = Color.White,
    secondary = colorInactivo,
    onSecondary = Color.White,
    background = Color(0xFFFFFFFF),
    onBackground = Color(0xFF000000),
    surface = Color(0xFFFFFFFF),
    onSurface = Color(0xFF000000),
    error = Color(0xFFB00020),
    onError = Color(0xFFFFFFFF)
)

// Paleta de colores para modo oscuro
private val DarkColors = darkColorScheme(
    primary = colorPrincipal,
    onPrimary = Color.Black,
    secondary = colorInactivoOscuro,
    onSecondary = Color.White,
    background = Color(0xFF121212),
    onBackground = Color(0xFFFFFFFF),
    surface = Color(0xFF121212),
    onSurface = Color(0xFFFFFFFF),
    error = Color(0xFFCF6679),
    onError = Color(0xFF000000)
)

// Tipografía por defecto de Material3
val Typography = Typography()

// Formas predeterminadas con esquinas redondeadas
val Shapes = Shapes(
    small = RoundedCornerShape(4.dp),
    medium = RoundedCornerShape(8.dp),
    large = RoundedCornerShape(12.dp)
)

// Composable que aplica el tema global
@Composable
fun MyFinanceTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkColors else LightColors

    MaterialTheme(
        colorScheme = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}