package com.example.myfinance.componentes

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

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

    val colorPrincipal = Color(0xFF6ADDB0)
    val colorInactivo = Color(0xFF757575)

    NavigationBar(
        containerColor = Color.White,
        tonalElevation = 8.dp,
        modifier = Modifier.height(86.dp)
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
                        tint = if (seleccionado) colorPrincipal else colorInactivo,
                        modifier = Modifier.size(24.dp)
                    )
                },
                label = {
                    Text(
                        text = item.label,
                        color = if (seleccionado) colorPrincipal else colorInactivo,
                        style = MaterialTheme.typography.labelSmall
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = colorPrincipal.copy(alpha = 0.1f)
                )
            )
        }
    }
}