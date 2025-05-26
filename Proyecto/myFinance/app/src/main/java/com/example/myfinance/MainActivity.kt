package com.example.myfinance

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myfinance.pantallas.PantallaCalculadora
import com.example.myfinance.pantallas.PantallaHistorial
import com.example.myfinance.pantallas.PantallaInicio
import com.example.myfinance.pantallas.PantallaPerfil
import com.example.myfinance.pantallas.PantallaTransacciones
import com.example.myfinance.theme.MyFinanceTheme
import com.example.myfinance.viewmodel.ThemeViewModel

/**
 * Activity principal que inicializa el tema, el ViewModel de tema y define
 * la navegación de Compose para las diferentes pantallas de la app.
 *
 * Gestiona rutas: inicio, historial, transacciones, calculadora y perfil.
 */
class MainActivity : ComponentActivity() {

    /**
     * Ciclo de vida onCreate donde se configura el contenido Compose.
     *
     * Crea el ThemeViewModel, aplica el tema dinámico y define
     * el NavHost con sus composables asociados.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // ViewModel que expone un StateFlow para el tema claro/oscuro
            val themeViewModel: ThemeViewModel = viewModel()
            // Observa cambios en el tema
            val isDarkTheme by themeViewModel.isDarkTheme.collectAsState()

            // Aplica el tema MyFinanceTheme según el estado de isDarkTheme
            MyFinanceTheme(darkTheme = isDarkTheme) {
                // Controlador de navegación para las rutas de la app
                val navController: NavHostController = rememberNavController()

                // Declaración de rutas composables para la navegación
                NavHost(
                    navController = navController,
                    startDestination = "inicio"
                ) {
                    composable("inicio") {
                        PantallaInicio(navController)           // Pantalla principal
                    }
                    composable("historial") {
                        PantallaHistorial(navController)       // Historial de transacciones
                    }
                    composable("transacciones") {
                        PantallaTransacciones(navController)   // Alta de transacciones
                    }
                    composable("calculadora") {
                        PantallaCalculadora(navController)     // Calculadora de interés
                    }
                    composable("perfil") {
                        // Pantalla de perfil, recibe callback para alternar tema
                        PantallaPerfil(
                            navController   = navController,
                            onToggleTheme   = { themeViewModel.toggleTheme(it) }
                        )
                    }
                }
            }
        }
    }
}