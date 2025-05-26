package com.example.myfinance.data.repository

import com.example.myfinance.data.local.dao.TransaccionDao
import com.example.myfinance.data.model.CategorySum
import com.example.myfinance.data.model.Transaccion
import kotlinx.coroutines.flow.Flow

/**
 * Repositorio que actúa como capa intermedia entre la fuente de datos
 * y la lógica de negocio para la entidad [Transaccion].
 *
 * Encapsula las operaciones CRUD y las consultas agregadas proporcionadas
 * por el [TransaccionDao].
 *
 * @property dao Instancia de [TransaccionDao] usada para acceder a Room.
 */
class TransaccionRepository(
    private val dao: TransaccionDao
) {

    /**
     * Inserta o reemplaza una transacción en la base de datos.
     *
     * @param transaccion Objeto [Transaccion] a insertar.
     * @return ID de la transacción insertada.
     */
    suspend fun insert(transaccion: Transaccion): Long =
        dao.insert(transaccion)

    /**
     * Recupera todas las transacciones ordenadas por fecha descendente.
     *
     * @return Lista de [Transaccion] obtenida desde la base de datos.
     */
    suspend fun getAll(): List<Transaccion> =
        dao.getAll()

    /**
     * Calcula el total de montos para un tipo de transacción dado.
     *
     * @param tipo Cadena que indica el tipo de transacción ("ingreso" o "gasto").
     * @return Suma total de montos para el tipo especificado.
     */
    suspend fun getTotalByTipo(tipo: String): Double =
        dao.getTotalByTipo(tipo)

    /**
     * Proporciona un flujo reactivo con la suma de montos por categoría
     * para un tipo de transacción.
     *
     * Ideal para observar cambios en tiempo real y actualizar gráficos o UI.
     *
     * @param tipo Tipo de transacción para filtrar ("ingreso" o "gasto").
     * @return [Flow] que emite listas de [CategorySum] (nombre de categoría y monto).
     */
    fun getSumByCategoria(tipo: String): Flow<List<CategorySum>> =
        dao.getSumByCategoriaFlow(tipo)
}
