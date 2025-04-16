package com.example.myfinance

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.myfinance.navigation.Screen
import com.example.myfinance.ui.PantallaCalculadora
import com.example.myfinance.ui.PantallaEstadisticas
import com.example.myfinance.ui.PantallaInicio
import com.example.myfinance.ui.PantallaPerfil
import com.example.myfinance.ui.PantallaTransacciones
import com.example.myfinance.components.BarraNavegacion
import com.example.myfinance.viewmodel.FinanceViewModel

@Composable
fun MyFinanceApp(viewModel: FinanceViewModel) {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            BarraNavegacion(
                rutaActual = navController.currentBackStackEntryAsState().value?.destination?.route ?: "",
                alSeleccionarItem = { ruta ->
                    navController.navigate(ruta) {
                        popUpTo(navController.graph.startDestinationId) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    ) { paddingInterno ->
        NavHost(
            navController = navController,
            startDestination = Screen.Inicio.ruta,
            modifier = Modifier.padding(paddingInterno)
        ) {
            composable(Screen.Transacciones.ruta) { PantallaTransacciones() }
            composable(Screen.Estadisticas.ruta) { PantallaEstadisticas() }
            composable(Screen.Inicio.ruta) { PantallaInicio(viewModel = viewModel) }
            composable(Screen.Calculadora.ruta) { PantallaCalculadora() }
            composable(Screen.Perfil.ruta) { PantallaPerfil() }
        }
    }
}