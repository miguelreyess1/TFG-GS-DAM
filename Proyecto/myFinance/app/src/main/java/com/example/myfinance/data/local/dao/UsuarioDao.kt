package com.example.myfinance.data.local.dao

import androidx.room.*
import com.example.myfinance.data.model.Usuario

@Dao
interface UsuarioDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(usuario: Usuario)

    @Query("SELECT * FROM usuario WHERE id = 0")
    suspend fun get(): Usuario?

    @Update
    suspend fun update(usuario: Usuario)
}