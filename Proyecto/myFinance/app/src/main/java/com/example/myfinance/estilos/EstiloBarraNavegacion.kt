package com.example.myfinance.estilos

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

object EstiloBarraNavegacion {
    val backgroundGradient: Brush
        @Composable get() = Brush.horizontalGradient(
            colors = listOf(
                Color(0xFF001F3F),
                Color(0xFF003366)
            )
        )

    val itemColor: Color = Color(0xFFFFFFFF)

    val selectedItemColor: Color = Color(0xFF001F3F)

    val height = 100.dp

    val iconSize = 24.dp
}
