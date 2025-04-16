package com.example.myfinance.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myfinance.model.Transaccion
import com.example.myfinance.repository.FinanceRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FinanceViewModel(private val repository: FinanceRepository) : ViewModel() {

    // Estado para el saldo y las transacciones de la hucha
    private val _saldoHucha = MutableStateFlow(0.0)
    val saldoHucha: StateFlow<Double> get() = _saldoHucha

    private val _transacciones = MutableStateFlow<List<Transaccion>>(emptyList())
    val transacciones: StateFlow<List<Transaccion>> get() = _transacciones

    // Por ahora se supone que el usuario tiene una única hucha con id = 1
    private val huchaId = 1

    init {
        inicializarHucha()
        cargarDatos()
    }

    private fun cargarDatos() {
        viewModelScope.launch {
            _saldoHucha.value = repository.getSaldoHucha(huchaId)
            _transacciones.value = repository.getTransacciones().filter { it.huchaId == huchaId }
        }
    }

    fun agregarTransaccion(transaccion: Transaccion) {
        viewModelScope.launch {
            repository.addTransaccion(transaccion)
            cargarDatos()
        }
    }

    // Inicializa la hucha por defecto si aún no existe
    private fun inicializarHucha() {
        viewModelScope.launch {
            val huchaExistente = repository.getHucha(huchaId)
            if (huchaExistente == null) {
                repository.addHucha(
                    com.example.myfinance.model.Hucha(
                        id = huchaId,
                        nombre = "Principal"
                    )
                )
            }
        }
    }
}
