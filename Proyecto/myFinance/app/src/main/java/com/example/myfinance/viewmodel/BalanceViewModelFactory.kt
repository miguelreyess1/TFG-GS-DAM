package com.example.myfinance.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myfinance.data.repository.TransaccionRepository

/**
 * Factory para crear instancias de [BalanceViewModel] proporcionando
 * un [TransaccionRepository] al constructor.
 *
 * Esto permite inyectar el repositorio en el ViewModel desde Compose.
 *
 * @property repository Repositorio de transacciones usado por el ViewModel.
 */
class BalanceViewModelFactory(
    private val repository: TransaccionRepository
) : ViewModelProvider.Factory {

    /**
     * Crea un ViewModel de la clase especificada.
     *
     * Si la clase solicitada es [BalanceViewModel], devuelve una nueva instancia
     * con el repositorio. En caso contrario lanza una excepción.
     *
     * @param modelClass Clase del ViewModel que se desea instanciar.
     * @return Una instancia de la clase de ViewModel solicitada.
     * @throws IllegalArgumentException Si la clase no es [BalanceViewModel].
     */
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BalanceViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return BalanceViewModel(repository) as T  // Crea BalanceViewModel con repositorio
        }
        // Error si se solicita otro tipo de ViewModel
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}