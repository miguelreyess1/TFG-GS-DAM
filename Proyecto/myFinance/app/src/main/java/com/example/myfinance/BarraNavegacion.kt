package com.example.myfinance

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.myfinance.estilos.EstiloBarraNavegacion

@Composable
fun BarraNavegacion(
    rutaActual: String,
    alSeleccionarItem: (String) -> Unit
) {
    NavigationBar(
        modifier = Modifier
            .height(EstiloBarraNavegacion.height)
            .shadow(elevation = 4.dp)
            .background(EstiloBarraNavegacion.backgroundGradient),
        containerColor = Color.Transparent
    ) {
        val items = listOf(
            Screen.Transacciones,
            Screen.Estadisticas,
            Screen.Inicio,
            Screen.Calculadora,
            Screen.Perfil
        )

        items.forEach { screen ->
            val selected = rutaActual == screen.ruta
            val iconSize by animateDpAsState(
                targetValue = if (selected) 32.dp else EstiloBarraNavegacion.iconSize,
                label = "iconSizeAnimation"
            )

            NavigationBarItem(
                icon = {
                    Box(
                        modifier = Modifier
                            .size(if (screen == Screen.Inicio && selected) 50.dp else 40.dp)
                            .clip(MaterialTheme.shapes.extraLarge)
                            .background(
                                if (screen == Screen.Inicio && selected)
                                    MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
                                else Color.Transparent
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = screen.icono,
                            contentDescription = "",
                            modifier = Modifier.size(iconSize),
                            tint = if (selected) EstiloBarraNavegacion.selectedItemColor
                            else EstiloBarraNavegacion.itemColor
                        )
                    }
                },
                selected = selected,
                onClick = { alSeleccionarItem(screen.ruta) },
                alwaysShowLabel = true
            )
        }
    }
}