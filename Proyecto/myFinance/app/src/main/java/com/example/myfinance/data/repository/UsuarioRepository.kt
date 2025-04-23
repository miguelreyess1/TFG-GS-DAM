package com.example.myfinance.data.repository

import com.example.myfinance.data.local.dao.UsuarioDao
import com.example.myfinance.data.model.Usuario

class UsuarioRepository(private val dao: UsuarioDao) {
    suspend fun insert(usuario: Usuario) = dao.insert(usuario)
    suspend fun update(usuario: Usuario) = dao.update(usuario)
    suspend fun get() = dao.get()
}
