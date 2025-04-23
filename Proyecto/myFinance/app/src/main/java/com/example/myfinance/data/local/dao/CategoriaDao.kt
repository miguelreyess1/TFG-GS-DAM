package com.example.myfinance.data.local.dao

import androidx.room.*
import com.example.myfinance.data.model.Categoria
@Dao
interface CategoriaDao {
    @Insert
    suspend fun insert(categoria: Categoria): Long

    @Update
    suspend fun update(categoria: Categoria)

    @Delete
    suspend fun delete(categoria: Categoria)

    @Query("SELECT * FROM categorias")
    suspend fun getAll(): List<Categoria>
}