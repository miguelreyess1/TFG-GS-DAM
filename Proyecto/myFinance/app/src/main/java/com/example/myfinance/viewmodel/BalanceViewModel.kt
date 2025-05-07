package com.example.myfinance.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myfinance.data.model.CategorySum
import com.example.myfinance.data.repository.TransaccionRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class BalanceViewModel(
    private val transaccionRepository: TransaccionRepository
) : ViewModel() {

    // Totales
    private val _totalIngresos = MutableStateFlow(0.0)
    val totalIngresos: StateFlow<Double> = _totalIngresos

    private val _totalGastos = MutableStateFlow(0.0)
    val totalGastos: StateFlow<Double> = _totalGastos

    private val _balanceTotal = MutableStateFlow(0.0)
    val balanceTotal: StateFlow<Double> = _balanceTotal

    // Estadísticas por categoría
    private val _statsGastos = MutableStateFlow<List<CategorySum>>(emptyList())
    val statsGastos: StateFlow<List<CategorySum>> = _statsGastos

    private val _statsIngresos = MutableStateFlow<List<CategorySum>>(emptyList())
    val statsIngresos: StateFlow<List<CategorySum>> = _statsIngresos

    init {
        cargarBalance()
        cargarStats()
    }

    private fun cargarBalance() {
        viewModelScope.launch {
            val ingresos = transaccionRepository.getTotalByTipo("ingreso")
            val gastos = transaccionRepository.getTotalByTipo("gasto")
            _totalIngresos.value = ingresos
            _totalGastos.value = gastos
            _balanceTotal.value = ingresos - gastos
        }
    }

    private fun cargarStats() {
        // Gastos
        viewModelScope.launch {
            transaccionRepository.sumByCategoriaFlow("gasto")
                .collect { lista ->
                    _statsGastos.value = lista
                }
        }
        // Ingresos
        viewModelScope.launch {
            transaccionRepository.sumByCategoriaFlow("ingreso")
                .collect { lista ->
                    _statsIngresos.value = lista
                }
        }
    }
}
