package com.example.myfinance.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
 * Factory para crear instancias de [UsuarioViewModel], proveyendo
 * el contexto de la aplicación al constructor.
 *
 * Permite inyectar el [Application] en el ViewModel desde Compose.
 *
 * @param application Contexto de la aplicación usado por el ViewModel.
 */
class UsuarioViewModelFactory(
    private val application: Application
) : ViewModelProvider.Factory {

    /**
     * Crea un ViewModel de la clase especificada.
     *
     * Si la clase solicitada es [UsuarioViewModel], devuelve una nueva instancia
     * con el contexto de la aplicación. En caso contrario lanza una excepción.
     *
     * @param modelClass Clase del ViewModel que se desea instanciar.
     * @return Una instancia de la clase de ViewModel solicitada.
     * @throws IllegalArgumentException Si la clase no es [UsuarioViewModel].
     */
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UsuarioViewModel::class.java)) {
            return UsuarioViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}
