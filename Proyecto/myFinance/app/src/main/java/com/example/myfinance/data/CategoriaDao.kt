package com.example.myfinance.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.myfinance.model.Categoria

@Dao
interface CategoriaDao {
    @Insert
    suspend fun insertCategoria(categoria: Categoria): Long

    @Query("SELECT * FROM categorias")
    suspend fun getAllCategorias(): List<Categoria>
}
