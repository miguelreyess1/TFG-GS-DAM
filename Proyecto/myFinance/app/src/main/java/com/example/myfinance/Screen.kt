package com.example.myfinance

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.PieChart
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val ruta: String, val etiqueta: String, val icono: ImageVector) {
    object Inicio : Screen("inicio", "Inicio", Icons.Filled.Home)
    object Transacciones : Screen("transacciones", "Transacciones", Icons.Filled.List)
    object Estadisticas : Screen("estadisticas", "Estadísticas", Icons.Filled.PieChart)
    object Calculadora : Screen("calculadora", "Calculadora", Icons.Filled.Calculate)
    object Perfil : Screen("perfil", "Perfil", Icons.Filled.Person)
}