package com.example.myfinance.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.myfinance.data.model.CategorySum
import com.example.myfinance.data.model.Transaccion
import kotlinx.coroutines.flow.Flow

@Dao
interface TransaccionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(transaccion: Transaccion): Long

    @Update
    suspend fun update(transaccion: Transaccion)

    @Delete
    suspend fun delete(transaccion: Transaccion)

    @Query("SELECT * FROM transacciones ORDER BY fecha DESC")
    suspend fun getAll(): List<Transaccion>

    @Query("SELECT IFNULL(SUM(monto), 0.0) FROM transacciones WHERE tipo = :tipo")
    suspend fun getTotalByTipo(tipo: String): Double

    @Query("""
    SELECT c.nombre AS nombre, 
           ABS(SUM(t.monto)) AS monto 
    FROM transacciones t 
    JOIN categorias c ON t.categoriaId = c.id 
    WHERE t.tipo = :tipo 
    GROUP BY c.nombre
""")
    fun getSumByCategoriaFlow(tipo: String): Flow<List<CategorySum>>
}
