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
    private val _totalIngresos = MutableStateFlow(0.0)
    val totalIngresos: StateFlow<Double> = _totalIngresos

    private val _totalGastos = MutableStateFlow(0.0)
    val totalGastos: StateFlow<Double> = _totalGastos

    private val _balanceTotal = MutableStateFlow(0.0)
    val balanceTotal: StateFlow<Double> = _balanceTotal

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
            val ingresos = transaccionRepository.getTotalByTipo("INGRESO")
            val gastos = transaccionRepository.getTotalByTipo("GASTO")
            _totalIngresos.value = ingresos
            _totalGastos.value = gastos
            _balanceTotal.value = ingresos - gastos
        }
    }

    private fun cargarStats() {
        viewModelScope.launch {
            transaccionRepository
                .getSumByCategoria("INGRESO")
                .collect { stats ->
                    _statsIngresos.value = stats
                }
        }
        viewModelScope.launch {
            transaccionRepository
                .getSumByCategoria("GASTO")
                .collect { stats ->
                    _statsGastos.value = stats
                }
        }
    }
}

