package com.example.myfinance.data.repository

import com.example.myfinance.data.local.dao.CategoriaDao
import com.example.myfinance.data.model.Categoria

class CategoriaRepository(private val dao: CategoriaDao) {
    suspend fun insert(categoria: Categoria) = dao.insert(categoria)
    suspend fun update(categoria: Categoria) = dao.update(categoria)
    suspend fun delete(categoria: Categoria) = dao.delete(categoria)
    suspend fun getAll() = dao.getAll()
}
