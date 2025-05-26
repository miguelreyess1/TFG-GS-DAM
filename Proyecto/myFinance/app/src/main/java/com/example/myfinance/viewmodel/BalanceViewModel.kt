package com.example.myfinance.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myfinance.data.model.CategorySum
import com.example.myfinance.data.repository.TransaccionRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel que encapsula la lógica para calcular el balance total,
 * totales de ingresos y gastos, y estadísticas de transacciones por categoría.
 *
 * @param transaccionRepository Repositorio para acceder a datos de transacciones.
 */
class BalanceViewModel(
    private val transaccionRepository: TransaccionRepository
) : ViewModel() {

    // Estado interno mutable para el total de ingresos
    private val _totalIngresos = MutableStateFlow(0.0)
    /** Flujo público que emite el total de ingresos. */
    val totalIngresos: StateFlow<Double> = _totalIngresos

    // Estado interno mutable para el total de gastos
    private val _totalGastos = MutableStateFlow(0.0)
    /** Flujo público que emite el total de gastos. */
    val totalGastos: StateFlow<Double> = _totalGastos

    // Estado interno mutable para el balance neto (ingresos - gastos)
    private val _balanceTotal = MutableStateFlow(0.0)
    /** Flujo público que emite el balance total. */
    val balanceTotal: StateFlow<Double> = _balanceTotal

    // Estado interno mutable para estadísticas de gastos por categoría
    private val _statsGastos = MutableStateFlow<List<CategorySum>>(emptyList())
    /** Flujo público que emite lista de CategorySum para gastos. */
    val statsGastos: StateFlow<List<CategorySum>> = _statsGastos

    // Estado interno mutable para estadísticas de ingresos por categoría
    private val _statsIngresos = MutableStateFlow<List<CategorySum>>(emptyList())
    /** Flujo público que emite lista de CategorySum para ingresos. */
    val statsIngresos: StateFlow<List<CategorySum>> = _statsIngresos

    init {
        // Al crearse el ViewModel, carga los datos iniciales
        cargarBalance()
        cargarStats()
    }

    /**
     * Carga los totales de ingresos y gastos desde el repositorio
     * y actualiza los correspondientes StateFlow.
     */
    private fun cargarBalance() {
        viewModelScope.launch {
            // Consulta total de ingresos y gastos
            val ingresos = transaccionRepository.getTotalByTipo("INGRESO")
            val gastos = transaccionRepository.getTotalByTipo("GASTO")
            // Actualiza los estados
            _totalIngresos.value = ingresos
            _totalGastos.value = gastos
            _balanceTotal.value = ingresos - gastos // Calcula balance neto
        }
    }

    /**
     * Carga las estadísticas de montos agrupados por categoría para
     * ingresos y gastos, suscribiéndose a los Flows del repositorio.
     */
    private fun cargarStats() {
        // Recoge estadísticas de ingresos por categoría
        viewModelScope.launch {
            transaccionRepository
                .getSumByCategoria("INGRESO")
                .collect { stats ->
                    _statsIngresos.value = stats
                }
        }
        // Recoge estadísticas de gastos por categoría
        viewModelScope.launch {
            transaccionRepository
                .getSumByCategoria("GASTO")
                .collect { stats ->
                    _statsGastos.value = stats
                }
        }
    }
}