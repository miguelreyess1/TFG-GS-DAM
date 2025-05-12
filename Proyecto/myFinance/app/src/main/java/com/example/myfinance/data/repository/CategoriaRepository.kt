package com.example.myfinance.data.repository

import com.example.myfinance.data.local.dao.CategoriaDao
import com.example.myfinance.data.model.Categoria

class CategoriaRepository(private val dao: CategoriaDao) {
    suspend fun getAll(): List<Categoria> =
        dao.getAll()

    suspend fun insert(categoria: Categoria): Long =
        dao.insert(categoria)
}
