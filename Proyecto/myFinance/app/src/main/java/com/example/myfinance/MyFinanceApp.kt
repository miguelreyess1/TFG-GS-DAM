package com.example.myfinance

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.composable
import com.example.myfinance.pantallas.*

@Composable
fun MyFinanceApp() {
    val navController: NavHostController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "inicio"
    ) {
        composable("inicio") { PantallaInicio(navController) }
        composable("historial") { PantallaHistorial(navController) }
        composable("transacciones") { PantallaTransacciones(navController) }
        composable("calculadora") { PantallaCalculadora(navController) }
        composable("perfil") { PantallaPerfil(navController) }
    }
}
