package com.example.myfinance.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.myfinance.data.model.Categoria

/**
 * Interfaz DAO para operaciones de base de datos sobre la entidad {@link Categoria}.
 *
 * Define métodos para insertar nuevas categorías y recuperar todas las categorías almacenadas.
 */
@Dao
interface CategoriaDao {

    /**
     * Inserta una nueva categoría en la tabla `categorias`.
     *
     * @param categoria Objeto {@link Categoria} a insertar.
     * @return El ID de la fila recién insertada en la base de datos.
     */
    @Insert
    suspend fun insert(categoria: Categoria): Long

    /**
     * Recupera todas las categorías almacenadas en la tabla `categorias`.
     *
     * @return Lista de objetos {@link Categoria} presentes en la base de datos.
     */
    @Query("SELECT * FROM categorias")
    suspend fun getAll(): List<Categoria>
}
