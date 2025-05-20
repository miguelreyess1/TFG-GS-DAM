package com.example.myfinance.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

private val LightColors = lightColorScheme(
    primary = Color(0xFF6ADDB0),
    primaryContainer = Color(0xFF8BC34A),
    secondary = Color(0xFF00BCD4),
    background = Color(0xFFFFFFFF),
    surface = Color(0xFFebffec),
    error = Color(0xFFB00020),
    onPrimary = Color(0xFF000000),
    onSecondary = Color(0xFF000000),
    onBackground = Color(0xFF000000),
    onSurface = Color(0xFF000000),
    onError = Color(0xFFFFFFFF)
)

private val DarkColors = darkColorScheme(
    primary = Color(0xFF6ADDB0),               // mismo verde que claro
    primaryContainer = Color(0xFF33691E),
    secondary = Color(0xFF00BCD4),
    background = Color(0xFF121212),            // gris muy oscuro
    surface = Color(0xFF121212),
    error = Color(0xFFCF6679),
    onPrimary = Color(0xFF000000),
    onSecondary = Color(0xFF000000),
    onBackground = Color(0xFFFFFFFF),
    onSurface = Color(0xFFFFFFFF),
    onError = Color(0xFF000000)
)

val Typography = Typography()

// ==== NUEVA LISTA DE COLORES VIBRANTES PARA GRÁFICOS ====
val ChartColors = listOf(
    Color(0xFF6ADDB0),
    Color(0xFF00BCD4),
    Color(0xFF8BC34A),
    Color(0xFF2196F3),                     // Azul vibrante
    Color(0xFF9C27B0),                     // Morado intenso
    Color(0xFFFFB74D),                     // Naranja suave
    Color(0xFFCF6679),                     // Rosa/error oscuro
    Color(0xFF4CAF50)                      // Verde complementario
)
// ================================================

val Shapes = Shapes(
    small  = RoundedCornerShape(4.dp),
    medium = RoundedCornerShape(8.dp),
    large  = RoundedCornerShape(12.dp)
)

@Composable
fun MyFinanceTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkColors else LightColors
    MaterialTheme(
        colorScheme = colors,
        typography  = Typography,
        shapes      = Shapes,
        content     = content
    )
}
