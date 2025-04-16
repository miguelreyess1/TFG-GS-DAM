package com.example.myfinance.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.myfinance.model.Transaccion

@Dao
interface TransaccionDao {
    @Insert
    suspend fun insertTransaccion(transaccion: Transaccion): Long

    @Query("SELECT * FROM transacciones ORDER BY fecha DESC")
    suspend fun getAllTransacciones(): List<Transaccion>

    @Query("""
        SELECT COALESCE(SUM(
            CASE 
                WHEN tipo = 'ingreso' THEN cantidad
                WHEN tipo = 'gasto' THEN -cantidad
                ELSE 0 
            END
        ), 0) FROM transacciones WHERE huchaId = :huchaId
    """)
    suspend fun getSaldoHucha(huchaId: Int): Double
}
