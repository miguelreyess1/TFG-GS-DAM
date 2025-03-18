package com.example.myfinance.estilos

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

object EstiloBarraNavegacion {
    // Gradiente personalizado: de azul oscuro a azul medio
    val backgroundGradient: Brush
        @Composable get() = Brush.horizontalGradient(
            colors = listOf(
                Color(0xFF001F3F),  // Azul oscuro
                Color(0xFF003366)   // Azul medio
            )
        )

    // Color de los ítems no seleccionados (blanco)
    val itemColor: Color = Color(0xFFFFFFFF)

    // Color de los ítems seleccionados (naranja)
    val selectedItemColor: Color = Color(0xFF001F3F)

    // Altura de la barra
    val height = 100.dp

    // Tamaño de los íconos
    val iconSize = 24.dp
}
