package com.example.myfinance

import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun BarraNavegacion(
    rutaActual: String,
    alSeleccionarItem: (String) -> Unit
) {
    NavigationBar {
        val items = listOf(
            Screen.Inicio,
            Screen.Transacciones,
            Screen.Estadisticas,
            Screen.Calculadora,
            Screen.Perfil
        )
        items.forEach { screen ->
            NavigationBarItem(
                icon = { Icon(imageVector = screen.icono, contentDescription = screen.etiqueta) },
                label = { Text(text = screen.etiqueta) },
                selected = rutaActual == screen.ruta,
                onClick = { alSeleccionarItem(screen.ruta) },
                alwaysShowLabel = true
            )
        }
    }
}
