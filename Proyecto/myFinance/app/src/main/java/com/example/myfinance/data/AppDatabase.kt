package com.example.myfinance.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.myfinance.model.Categoria
import com.example.myfinance.model.Hucha
import com.example.myfinance.model.Transaccion

@Database(entities = [Categoria::class, Hucha::class, Transaccion::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun categoriaDao(): CategoriaDao
    abstract fun huchaDao(): HuchaDao
    abstract fun transaccionDao(): TransaccionDao
}