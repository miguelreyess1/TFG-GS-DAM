package com.example.myfinance.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.PieChart
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val ruta: String, val icono: ImageVector) {
    object Inicio : Screen("inicio", Icons.Filled.Home)
    object Transacciones : Screen("transacciones",  Icons.AutoMirrored.Filled.List)
    object Estadisticas : Screen("estadisticas",  Icons.Filled.PieChart)
    object Calculadora : Screen("calculadora", Icons.Filled.Calculate)
    object Perfil : Screen("perfil", Icons.Filled.Person)
}