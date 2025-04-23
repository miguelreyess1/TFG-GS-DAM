package com.example.myfinance.navegacion

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.myfinance.pantallas.*
import androidx.navigation.compose.composable

@Composable
fun AppNavegacion() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "inicio") {
        composable("inicio") { PantallaInicio(navController) }
        composable("historial") { PantallaHistorial(navController) }
        composable("transacciones") { PantallaTransacciones(navController) }
        composable("calculadora") { PantallaCalculadora(navController) }
        composable("perfil") { PantallaPerfil(navController) }
    }
}
