package com.example.myfinance.data.repository

import com.example.myfinance.data.local.dao.CategoriaDao
import com.example.myfinance.data.model.Categoria

/**
 * Repositorio que actúa como capa de acceso a datos para la entidad {@link Categoria}.
 *
 * Encapsula las llamadas al DAO y proporciona una API limpia para obtener e insertar
 * categorías desde/c hacia la base de datos.
 *
 * @property dao Instancia de [CategoriaDao] para realizar operaciones de Room.
 */
class CategoriaRepository(
    private val dao: CategoriaDao
) {

    /**
     * Recupera todas las categorías almacenadas.
     *
     * @return Lista de [Categoria] obtenida desde la base de datos.
     */
    suspend fun getAll(): List<Categoria> =
        dao.getAll()

    /**
     * Inserta una nueva categoría en la base de datos.
     *
     * @param categoria Objeto [Categoria] a insertar.
     * @return ID de la fila insertada.
     */
    suspend fun insert(categoria: Categoria): Long =
        dao.insert(categoria)
}
