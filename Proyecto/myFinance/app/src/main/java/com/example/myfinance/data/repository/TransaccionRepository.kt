package com.example.myfinance.data.repository

import com.example.myfinance.data.local.dao.TransaccionDao
import com.example.myfinance.data.model.Transaccion
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class TransaccionRepository(private val dao: TransaccionDao) {
    suspend fun insert(transaccion: Transaccion) = dao.insert(transaccion)
    suspend fun getAll() = dao.getAll()
    suspend fun getTotalByTipo(tipo: String) = dao.getTotalByTipo(tipo)
    fun sumByCategoriaFlow(tipo: String) = flow {
        emit(dao.getSumByCategoria(tipo))
    }.flowOn(Dispatchers.IO)
}
