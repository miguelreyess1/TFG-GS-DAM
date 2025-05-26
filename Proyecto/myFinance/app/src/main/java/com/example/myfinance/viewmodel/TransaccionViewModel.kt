package com.example.myfinance.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myfinance.data.model.Transaccion
import com.example.myfinance.data.repository.TransaccionRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel que expone la lista de transacciones almacenadas en la base de datos.
 *
 * @param repository Repositorio de transacciones usado para cargar los datos.
 */
class TransaccionViewModel(
    private val repository: TransaccionRepository
) : ViewModel() {

    // Estado interno mutable que almacena la lista de transacciones
    private val _transacciones = MutableStateFlow<List<Transaccion>>(emptyList())
    /** Flujo público que emite actualizaciones de la lista de transacciones. */
    val transacciones: StateFlow<List<Transaccion>> = _transacciones.asStateFlow()

    init {
        // Carga inicial de transacciones al crear el ViewModel
        cargarTransacciones()
    }

    /**
     * Consulta todas las transacciones desde el repositorio y actualiza el estado.
     */
    private fun cargarTransacciones() {
        viewModelScope.launch {
            _transacciones.value = repository.getAll()  // Asigna lista obtenida
        }
    }
}
