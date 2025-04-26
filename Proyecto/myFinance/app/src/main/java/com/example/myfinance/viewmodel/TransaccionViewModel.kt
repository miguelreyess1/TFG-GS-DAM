package com.example.myfinance.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myfinance.data.model.Transaccion
import com.example.myfinance.data.repository.TransaccionRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TransaccionViewModel(
    private val repository: TransaccionRepository
) : ViewModel() {

    private val _transacciones = MutableStateFlow<List<Transaccion>>(emptyList())
    val transacciones: StateFlow<List<Transaccion>> = _transacciones

    init {
        cargarTransacciones()
    }

    private fun cargarTransacciones() {
        viewModelScope.launch {
            _transacciones.value = repository.getAll()
        }
    }
}
