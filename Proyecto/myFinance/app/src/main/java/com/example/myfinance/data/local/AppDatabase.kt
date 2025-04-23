package com.example.myfinance.data.local

import android.content.Context
import androidx.room.*
import com.example.myfinance.data.model.*
import com.example.myfinance.data.local.dao.*
import com.example.myfinance.data.model.Transaccion

@Database(
    entities = [Transaccion::class, Categoria::class, Usuario::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(RoomTypeConverters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun transaccionDao(): TransaccionDao
    abstract fun categoriaDao(): CategoriaDao
    abstract fun usuarioDao(): UsuarioDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "myfinance_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
