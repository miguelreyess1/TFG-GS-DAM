/**
 * Define los esquemas de color y la configuración de tema para la aplicación MyFinance.
 *
 * Incluye esquemas de colores para modo claro y oscuro, paleta de colores para gráficos,
 * tipografías y formas personalizadas.
 */
package com.example.myfinance.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

// ==== Esquema de colores para modo claro ====
private val LightColors = lightColorScheme(
    primary            = Color(0xFF6ADDB0), // Verde principal claro
    primaryContainer   = Color(0xFF8BC34A), // Contenedor de verde
    secondary          = Color(0xFF00BCD4), // Azul secundario
    background         = Color(0xFFFFFFFF), // Fondo blanco
    surface            = Color(0xFFebffec), // Superficie ligeramente verdeada
    error              = Color(0xFFB00020), // Rojo de error
    onPrimary          = Color(0xFF000000), // Texto sobre primary
    onSecondary        = Color(0xFF000000), // Texto sobre secondary
    onBackground       = Color(0xFF000000), // Texto sobre background
    onSurface          = Color(0xFF000000), // Texto sobre surface
    onError            = Color(0xFFFFFFFF)  // Texto sobre error
)

// ==== Esquema de colores para modo oscuro ====
private val DarkColors = darkColorScheme(
    primary            = Color(0xFF6ADDB0), // Mismo verde que en claro
    primaryContainer   = Color(0xFF33691E), // Verde oscuro para contenedores
    secondary          = Color(0xFF00BCD4), // Azul secundario
    background         = Color(0xFF121212), // Fondo casi negro
    surface            = Color(0xFF121212), // Superficie igual al fondo
    error              = Color(0xFFCF6679), // Rosa/error oscuro
    onPrimary          = Color(0xFF000000), // Texto sobre primary
    onSecondary        = Color(0xFF000000), // Texto sobre secondary
    onBackground       = Color(0xFFFFFFFF), // Texto sobre background
    onSurface          = Color(0xFFFFFFFF), // Texto sobre surface
    onError            = Color(0xFF000000)  // Texto sobre error
)

// Tipografías por defecto (pueden personalizarse más adelante)
val Typography = Typography()

// ==== Paleta de colores vibrantes para gráficos ====
val ChartColors = listOf(
    Color(0xFF6ADDB0), // Verde principal
    Color(0xFF00BCD4), // Cian brillante
    Color(0xFF8BC34A), // Verde lima
    Color(0xFF2196F3), // Azul vibrante
    Color(0xFF9C27B0), // Morado intenso
    Color(0xFFFFB74D), // Naranja suave
    Color(0xFFCF6679), // Rosa/error oscuro
    Color(0xFF4CAF50)  // Verde complementario
)

// Formas redondeadas por tamaño
val Shapes = Shapes(
    small  = RoundedCornerShape(4.dp),  // Esquinas ligeramente redondeadas
    medium = RoundedCornerShape(8.dp),  // Esquinas moderadamente redondeadas
    large  = RoundedCornerShape(12.dp)  // Esquinas más redondeadas
)

/**
 * Composable que aplica el tema MyFinance a su contenido.
 *
 * Detecta el modo claro/oscuro del sistema por defecto, pero permite
 * forzar el modo oscuro si se pasa darkTheme = true.
 *
 * @param darkTheme Si true, usa DarkColors; si false, usa LightColors.
 * @param content   Contenido Compose al que se aplicará el tema.
 */
@Composable
fun MyFinanceTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    // Selecciona el esquema de colores adecuado
    val colors = if (darkTheme) DarkColors else LightColors

    // Aplica MaterialTheme con nuestros colores, tipografías y formas
    MaterialTheme(
        colorScheme = colors,
        typography  = Typography,
        shapes      = Shapes,
        content     = content
    )
}