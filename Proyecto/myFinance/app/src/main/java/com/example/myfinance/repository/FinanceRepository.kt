package com.example.myfinance.repository

import com.example.myfinance.data.AppDatabase
import com.example.myfinance.model.Categoria
import com.example.myfinance.model.Hucha
import com.example.myfinance.model.Transaccion

class FinanceRepository(private val db: AppDatabase) {

    // Operaciones con Categorías
    suspend fun addCategoria(categoria: Categoria): Long = db.categoriaDao().insertCategoria(categoria)
    suspend fun getCategorias(): List<Categoria> = db.categoriaDao().getAllCategorias()

    // Operaciones con Huchas
    suspend fun addHucha(hucha: Hucha): Long = db.huchaDao().insertHucha(hucha)
    suspend fun getHucha(huchaId: Int) = db.huchaDao().getHucha(huchaId)
    suspend fun updateHucha(hucha: Hucha) = db.huchaDao().updateHucha(hucha)
    suspend fun getAllHuchas(): List<Hucha> = db.huchaDao().getAllHuchas()

    // Operaciones con Transacciones
    suspend fun addTransaccion(transaccion: Transaccion): Long = db.transaccionDao().insertTransaccion(transaccion)
    suspend fun getTransacciones(): List<Transaccion> = db.transaccionDao().getAllTransacciones()
    suspend fun getSaldoHucha(huchaId: Int): Double = db.transaccionDao().getSaldoHucha(huchaId)
}
