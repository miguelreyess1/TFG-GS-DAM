package com.example.myfinance.data.local.dao

import androidx.room.*
import com.example.myfinance.data.model.Usuario

/**
 * DAO para la entidad {@link Usuario}, gestiona las operaciones
 * de inserción, consulta y actualización del usuario actual.
 */
@Dao
interface UsuarioDao {

    /**
     * Inserta o reemplaza la información del usuario en la tabla `usuario`.
     *
     * @param usuario Objeto {@link Usuario} a insertar o reemplazar.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(usuario: Usuario)

    /**
     * Recupera el usuario con ID 0 (usuario principal/predefinido).
     *
     * Asume que sólo existe una fila con id = 0 para almacenar los datos del usuario activo.
     *
     * @return El {@link Usuario} si existe, o null si no se ha insertado ninguno.
     */
    @Query("SELECT * FROM usuario WHERE id = 0")
    suspend fun get(): Usuario?

    /**
     * Actualiza los datos del usuario existente en la base de datos.
     *
     * @param usuario Objeto {@link Usuario} con los nuevos valores.
     */
    @Update
    suspend fun update(usuario: Usuario)
}
