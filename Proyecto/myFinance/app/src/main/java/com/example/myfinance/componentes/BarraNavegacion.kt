package com.example.myfinance.componentes

import androidx.compose.ui.graphics.Color
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.unit.dp

data class NavItem(val route: String, val icon: ImageVector, val label: String)

val items = listOf(
    NavItem("inicio", Icons.Default.Home, "Inicio"),
    NavItem("historial", Icons.Default.History, "Historial"),
    NavItem("transacciones", Icons.Default.Add, "Añadir"),
    NavItem("calculadora", Icons.Default.Calculate, "Calculadora"),
    NavItem("perfil", Icons.Default.Person, "Perfil")
)

@Composable
fun BarraNavegacion(navController: NavController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val colorSeleccionado = Color(0xFF66DABE)
    val colorNoSeleccionado = Color.Gray

    NavigationBar(
        containerColor = Color.White,
        tonalElevation = 8.dp
    ) {
        items.forEach { item ->
            val seleccionado = currentRoute == item.route

            NavigationBarItem(
                selected = seleccionado,
                onClick = { if (!seleccionado) navController.navigate(item.route) },
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.label,
                        tint = if (seleccionado) colorSeleccionado else colorNoSeleccionado
                    )
                },
                label = {
                    Text(
                        text = item.label,
                        color = if (seleccionado) colorSeleccionado else colorNoSeleccionado
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = colorSeleccionado.copy(alpha = 0.1f)
                )
            )
        }
    }
}