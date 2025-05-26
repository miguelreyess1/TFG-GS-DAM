package com.example.myfinance.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myfinance.data.repository.TransaccionRepository

/**
 * Factory para crear instancias de [TransaccionViewModel] proporcionando
 * un [TransaccionRepository] al constructor.
 *
 * Permite inyectar el repositorio en el ViewModel desde Compose.
 *
 * @param repository Repositorio de transacciones usado por el ViewModel.
 */
class TransaccionViewModelFactory(
    private val repository: TransaccionRepository
) : ViewModelProvider.Factory {

    /**
     * Crea un ViewModel de la clase especificada.
     *
     * Si la clase solicitada es [TransaccionViewModel], devuelve una nueva instancia
     * con el repositorio. En caso contrario, lanza una excepción.
     *
     * @param modelClass Clase del ViewModel que se desea instanciar.
     * @return Una instancia de la clase de ViewModel solicitada.
     * @throws IllegalArgumentException Si la clase no es [TransaccionViewModel].
     */
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(TransaccionViewModel::class.java)) {
            TransaccionViewModel(repository) as T  // Crea TransaccionViewModel con el repositorio
        } else {
            // Error si se solicita otro tipo de ViewModel
            throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }
}
