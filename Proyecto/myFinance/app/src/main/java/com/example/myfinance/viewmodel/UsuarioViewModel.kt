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

data class PerfilUiState(
    val nombre: String,
    val imageUri: String?
)

class UsuarioViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: UsuarioRepository
    private val _profileState = MutableStateFlow<PerfilUiState?>(null)
    val profileState = _profileState.asStateFlow()

    init {
        val dao = AppDatabase.getInstance(application).usuarioDao()
        repository = UsuarioRepository(dao)

        viewModelScope.launch {
            val usuario = repository.get()
            _profileState.value = usuario?.let {
                PerfilUiState(it.nombre, it.imageUri)
            }
        }
    }

    fun saveProfile(nombre: String, imageUri: String?) {
        viewModelScope.launch {
            val usuario = Usuario(nombre = nombre, imageUri = imageUri)
            repository.insert(usuario)
            _profileState.value = PerfilUiState(nombre, imageUri)
        }
    }
}
