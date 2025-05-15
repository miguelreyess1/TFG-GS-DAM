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

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val themeViewModel: ThemeViewModel = viewModel()
            val isDarkTheme by themeViewModel.isDarkTheme.collectAsState()

            MyFinanceTheme(darkTheme = isDarkTheme) {
                val navController: NavHostController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = "inicio"
                ) {
                    composable("inicio") {
                        PantallaInicio(navController)
                    }
                    composable("historial") {
                        PantallaHistorial(navController)
                    }
                    composable("transacciones") {
                        PantallaTransacciones(navController)
                    }
                    composable("calculadora") {
                        PantallaCalculadora(navController)
                    }
                    // Ahora PantallaPerfil recibe tema y toggle
                    composable("perfil") {
                        PantallaPerfil(
                            navController   = navController,
                            isDarkTheme     = isDarkTheme,
                            onToggleTheme   = { themeViewModel.toggleTheme(it) }
                        )
                    }
                }
            }
        }
    }
}