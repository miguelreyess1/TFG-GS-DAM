package com.example.myfinance.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel que gestiona el tema de la aplicación (claro u oscuro).
 *
 * Mantiene un StateFlow booleano que indica si el tema actual es oscuro.
 */
class ThemeViewModel : ViewModel() {

    // Estado interno mutable que almacena si el tema es oscuro
    private val _isDarkTheme = MutableStateFlow(false)

    /** Flujo público que emite cambios en el estado del tema (true = oscuro). */
    val isDarkTheme: StateFlow<Boolean> = _isDarkTheme

    /**
     * Cambia el tema de la aplicación.
     *
     * @param dark True para activar el tema oscuro; false para el tema claro.
     */
    fun toggleTheme(dark: Boolean) {
        // Emite el nuevo estado de tema de forma asíncrona
        viewModelScope.launch {
            _isDarkTheme.emit(dark)
        }
    }
}