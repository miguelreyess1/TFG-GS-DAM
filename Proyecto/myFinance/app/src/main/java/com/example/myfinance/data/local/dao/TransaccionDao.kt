package com.example.myfinance.data.local.dao

import androidx.room.*
import com.example.myfinance.data.model.Transaccion

@Dao
interface TransaccionDao {
    @Insert
    suspend fun insert(transaccion: Transaccion): Long

    @Update
    suspend fun update(transaccion: Transaccion)

    @Delete
    suspend fun delete(transaccion: Transaccion)

    @Query("SELECT * FROM transacciones ORDER BY fecha DESC")
    suspend fun getAll(): List<Transaccion>

    @Query("SELECT SUM(monto) FROM transacciones WHERE tipo = :tipo")
    suspend fun getTotalByTipo(tipo: String): Double?
}