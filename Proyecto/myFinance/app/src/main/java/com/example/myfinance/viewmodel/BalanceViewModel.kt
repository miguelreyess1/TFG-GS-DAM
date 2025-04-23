package com.example.myfinance.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myfinance.data.repository.TransaccionRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class BalanceViewModel(
    private val transaccionRepository: TransaccionRepository // Inyección manual
) : ViewModel() {

    private val _balanceTotal = MutableStateFlow(0.0)
    val balanceTotal: StateFlow<Double> = _balanceTotal

    init {
        cargarBalance()
    }

    private fun cargarBalance() {
        viewModelScope.launch {
            val ingresos = transaccionRepository.getTotalByTipo("ingreso")
            val gastos = transaccionRepository.getTotalByTipo("gasto")
            _balanceTotal.value = ingresos - gastos
        }
    }
}