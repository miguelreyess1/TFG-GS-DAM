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

/**
 * Interfaz DAO para operaciones sobre la entidad {@link Transaccion}.
 *
 * Proporciona métodos para insertar, actualizar, eliminar y consultar
 * transacciones, así como cálculos agregados por tipo y categoría.
 */
@Dao
interface TransaccionDao {

    /**
     * Inserta una transacción en la base de datos.
     *
     * En caso de conflicto (mismo ID), reemplaza la transacción existente.
     *
     * @param transaccion Objeto {@link Transaccion} a insertar o reemplazar.
     * @return ID de la fila insertada.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(transaccion: Transaccion): Long

    /**
     * Actualiza una transacción existente en la base de datos.
     *
     * @param transaccion Objeto {@link Transaccion} con los nuevos valores.
     */
    @Update
    suspend fun update(transaccion: Transaccion)

    /**
     * Elimina una transacción de la base de datos.
     *
     * @param transaccion Objeto {@link Transaccion} a eliminar.
     */
    @Delete
    suspend fun delete(transaccion: Transaccion)

    /**
     * Recupera todas las transacciones, ordenadas por fecha descendente.
     *
     * @return Lista de {@link Transaccion} ordenada de la más reciente a la más antigua.
     */
    @Query("SELECT * FROM transacciones ORDER BY fecha DESC")
    suspend fun getAll(): List<Transaccion>

    /**
     * Calcula el total de montos para un tipo de transacción dado.
     *
     * @param tipo Tipo de transacción ("ingreso", "gasto", etc.).
     * @return Suma total de los montos para el tipo especificado.
     */
    @Query("SELECT IFNULL(SUM(monto), 0.0) FROM transacciones WHERE tipo = :tipo")
    suspend fun getTotalByTipo(tipo: String): Double

    /**
     * Obtiene el flujo de sumas de montos por categoría para un tipo de transacción.
     *
     * Emite una lista de objetos {@link CategorySum} que contienen el nombre
     * de la categoría y la suma absoluta de los montos asociados.
     *
     * @param tipo Tipo de transacción para filtrar ("ingreso", "gasto", etc.).
     * @return Flow que emite listas de {@link CategorySum} con nombre y monto.
     */
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
