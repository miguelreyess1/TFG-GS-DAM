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
    entities = [Transaccion::class, Usuario::class, Categoria::class],
    version = 6,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun transaccionDao(): TransaccionDao
    abstract fun usuarioDao(): UsuarioDao
    abstract fun categoriaDao(): CategoriaDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
