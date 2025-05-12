package com.example.myfinance.data.repository

import com.example.myfinance.data.local.dao.TransaccionDao
import com.example.myfinance.data.model.CategorySum
import com.example.myfinance.data.model.Transaccion
import kotlinx.coroutines.flow.Flow

class TransaccionRepository(private val dao: TransaccionDao) {
    suspend fun insert(transaccion: Transaccion) = dao.insert(transaccion)
    suspend fun getAll() = dao.getAll()
    suspend fun getTotalByTipo(tipo: String) = dao.getTotalByTipo(tipo)

    fun getSumByCategoria(tipo: String): Flow<List<CategorySum>> {
        return dao.getSumByCategoriaFlow(tipo)
    }
}
