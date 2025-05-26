package com.example.myfinance.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.myfinance.data.local.AppDatabase
import com.example.myfinance.data.model.Usuario
import com.example.myfinance.data.repository.UsuarioRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * Estado de UI para el perfil de usuario.
 *
 * @property nombre    Nombre del usuario.
 * @property imageUri  URI de la imagen de perfil, o null si no hay imagen.
 */
data class PerfilUiState(
    val nombre: String,
    val imageUri: String?
)

/**
 * ViewModel que gestiona la carga y guardado de los datos de perfil del usuario.
 *
 * Extiende AndroidViewModel para tener acceso al contexto de la aplicación,
 * necesario para obtener la instancia de la base de datos.
 *
 * @param application Contexto de la aplicación, usado para inicializar la base de datos.
 */
class UsuarioViewModel(application: Application) : AndroidViewModel(application) {

    // Repositorio que abstrae el acceso a la tabla de usuario en Room
    private val repository: UsuarioRepository

    // Estado interno mutable que almacena los datos de perfil (o null si todavía no se han cargado)
    private val _profileState = MutableStateFlow<PerfilUiState?>(null)

    /** Flujo público que emite los cambios en el estado del perfil de usuario. */
    val profileState = _profileState.asStateFlow()

    init {
        // Inicializa el repositorio usando el DAO de Usuario de la base de datos
        val dao = AppDatabase.getDatabase(application).usuarioDao()
        repository = UsuarioRepository(dao)

        // Carga los datos del usuario existente en la base de datos al iniciar
        viewModelScope.launch {
            val usuario = repository.get()  // Puede ser null si nunca se guardó
            _profileState.value = usuario?.let {
                // Transforma el modelo de datos a PerfilUiState para la UI
                PerfilUiState(it.nombre, it.imageUri)
            }
        }
    }

    /**
     * Guarda los cambios del perfil (nombre e imagen) en la base de datos
     * y actualiza el estado de UI inmediatamente.
     *
     * @param nombre   Nuevo nombre del usuario.
     * @param imageUri Nueva URI de la imagen de perfil, o null para eliminarla.
     */
    fun saveProfile(nombre: String, imageUri: String?) {
        viewModelScope.launch {
            // Crea o reemplaza el registro de Usuario con ID fijo = 0
            val usuario = Usuario(nombre = nombre, imageUri = imageUri)
            repository.insert(usuario)
            // Actualiza el estado para que la UI refleje los cambios
            _profileState.value = PerfilUiState(nombre, imageUri)
        }
    }
}