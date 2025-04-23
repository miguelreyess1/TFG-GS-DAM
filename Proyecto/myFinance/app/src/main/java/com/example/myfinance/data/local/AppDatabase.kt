package com.example.myfinance.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.myfinance.data.local.dao.CategoriaDao
import com.example.myfinance.data.local.dao.TransaccionDao
import com.example.myfinance.data.local.dao.UsuarioDao
import com.example.myfinance.data.model.Categoria
import com.example.myfinance.data.model.Transaccion
import com.example.myfinance.data.model.Usuario

@Database(
    entities = [Transaccion::class, Categoria::class, Usuario::class],
    version = 2,
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