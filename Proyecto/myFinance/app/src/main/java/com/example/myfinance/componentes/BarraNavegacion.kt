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

/**
 * Representa un elemento de la barra de navegación inferior.
 *
 * @property route Ruta de navegación asociada al ítem.
 * @property icon Ícono que se muestra en la barra.
 * @property label Texto descriptivo que acompaña al ícono.
 */
data class NavItem(
    val route: String,
    val icon: ImageVector,
    val label: String
)

/**
 * Lista de elementos que aparecerán en la barra de navegación.
 * Cada NavItem define su ruta, su icono por defecto y su etiqueta.
 */
val items = listOf(
    NavItem("inicio", Icons.Default.Home, "Inicio"),
    NavItem("historial", Icons.Default.History, "Historial"),
    NavItem("transacciones", Icons.Default.Add, "Añadir"),
    NavItem("calculadora", Icons.Default.Calculate, "Calculadora"),
    NavItem("perfil", Icons.Default.Person, "Perfil")
)

/**
 * Composable que muestra una barra de navegación inferior basada en Material 3.
 *
 * Obtiene la ruta actual desde el NavController y pinta cada NavigationBarItem
 * con colores activos/inactivos según si está seleccionado.
 *
 * @param navController Controlador de navegación usado para cambiar de pantallas.
 */
@Composable
fun BarraNavegacion(navController: NavController) {
    // Observa la entrada de la pila de navegación para obtener la ruta actual
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    // Colores del tema de la app
    val activeColor = MaterialTheme.colorScheme.primary
    val inactiveColor = MaterialTheme.colorScheme.onSurfaceVariant
    val backgroundColor = MaterialTheme.colorScheme.surface

    // Contenedor de la barra con altura fija y elevación
    NavigationBar(
        containerColor = backgroundColor,
        tonalElevation = 8.dp,
        modifier = Modifier.height(86.dp)
    ) {
        // Itera sobre cada elemento definido y crea su ítem en la barra
        items.forEach { item ->
            val selected = currentRoute == item.route

            NavigationBarItem(
                selected = selected,
                // Navega a la ruta del ítem sólo si no está ya seleccionada
                onClick = { if (!selected) navController.navigate(item.route) },
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.label,
                        // Aplica color según estado seleccionado
                        tint = if (selected) activeColor else inactiveColor,
                        modifier = Modifier.size(24.dp)
                    )
                },
                label = {
                    Text(
                        text = item.label,
                        color = if (selected) activeColor else inactiveColor,
                        style = MaterialTheme.typography.labelSmall
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = activeColor,
                    unselectedIconColor = inactiveColor,
                    selectedTextColor = activeColor,
                    unselectedTextColor = inactiveColor,
                    // Sin indicador de fondo
                    indicatorColor = Color.Transparent
                )
            )
        }
    }
}
