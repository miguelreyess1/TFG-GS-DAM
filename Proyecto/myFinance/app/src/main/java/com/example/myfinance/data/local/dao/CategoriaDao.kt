package com.example.myfinance.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.myfinance.data.model.Categoria

@Dao
interface CategoriaDao {
    @Insert
    suspend fun insert(categoria: Categoria): Long

    @Query("SELECT * FROM categorias")
    suspend fun getAll(): List<Categoria>
}
