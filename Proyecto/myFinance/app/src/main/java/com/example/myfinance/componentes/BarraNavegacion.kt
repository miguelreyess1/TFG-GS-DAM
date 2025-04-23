package com.example.myfinance.componentes

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*

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

    NavigationBar {
        items.forEach { item ->
            NavigationBarItem(
                selected = currentRoute == item.route,
                onClick = {
                    if (currentRoute != item.route) {
                        navController.navigate(item.route)
                    }
                },
                icon = { Icon(imageVector = item.icon, contentDescription = item.label) },
                label = { Text(item.label) }
            )
        }
    }
}
