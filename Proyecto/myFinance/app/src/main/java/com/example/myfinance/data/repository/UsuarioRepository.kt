package com.example.myfinance.data.repository

import com.example.myfinance.data.local.dao.UsuarioDao
import com.example.myfinance.data.model.Usuario

/**
 * Repositorio que sirve de capa de acceso a datos para la entidad [Usuario].
 *
 * Encapsula las operaciones de inserción y consulta del usuario principal
 * almacenado en la base de datos.
 *
 * @property dao Instancia de [UsuarioDao] utilizada para realizar las operaciones CRUD.
 */
class UsuarioRepository(
    private val dao: UsuarioDao
) {

    /**
     * Inserta o reemplaza la información del usuario en la tabla `usuario`.
     *
     * @param usuario Objeto [Usuario] a insertar o reemplazar.
     */
    suspend fun insert(usuario: Usuario): Unit =
        dao.insert(usuario)

    /**
     * Recupera el usuario principal (ID = 0) de la base de datos.
     *
     * @return Instancia de [Usuario] si existe, o null si no se ha insertado ningún usuario.
     */
    suspend fun get(): Usuario? =
        dao.get()
}
