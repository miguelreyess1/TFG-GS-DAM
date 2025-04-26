package com.example.myfinance.data.repository

import com.example.myfinance.data.local.dao.TransaccionDao
import com.example.myfinance.data.model.Transaccion

class TransaccionRepository(private val dao: TransaccionDao) {
    suspend fun insert(transaccion: Transaccion) = dao.insert(transaccion)
    suspend fun update(transaccion: Transaccion) = dao.update(transaccion)
    suspend fun delete(transaccion: Transaccion) = dao.delete(transaccion)
    suspend fun getAll() = dao.getAll()
    suspend fun getTotalByTipo(tipo: String) = dao.getTotalByTipo(tipo)
}
