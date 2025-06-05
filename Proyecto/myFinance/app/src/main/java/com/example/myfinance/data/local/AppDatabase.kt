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

/**
 * Base de datos de la aplicación usando Room.
 *
 * Define las entidades, la versión y los conversores de tipos. Proporciona
 * métodos abstractos para obtener cada DAO y un singleton para acceder
 * a la instancia de la base de datos.
 *
 * @see TransaccionDao
 * @see UsuarioDao
 * @see CategoriaDao
 */
@Database(
    entities = [Transaccion::class, Usuario::class, Categoria::class],
    version = 7,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    /**
     * DAO para operaciones sobre transacciones.
     */
    abstract fun transaccionDao(): TransaccionDao

    /**
     * DAO para operaciones sobre el usuario.
     */
    abstract fun usuarioDao(): UsuarioDao

    /**
     * DAO para operaciones sobre categorías.
     */
    abstract fun categoriaDao(): CategoriaDao

    companion object {
        // Volatile garantiza que la instancia se lee siempre desde memoria principal
        @Volatile
        private var INSTANCE: AppDatabase? = null

        /**
         * Obtiene la instancia singleton de AppDatabase.
         *
         * Si aún no existe, la crea usando Room.databaseBuilder con
         * fallbackToDestructiveMigration para reconstruir la base de datos
         * en caso de cambios de versión sin migraciones definidas.
         *
         * @param context Contexto de la aplicación para acceder al sistema de archivos.
         * @return Instancia única de AppDatabase.
         */
        fun getDatabase(context: Context): AppDatabase {
            // Si ya existe instancia, la retorna; si no, crea una nueva de forma sincronizada
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"             // Nombre del archivo de base de datos
                )
                    .fallbackToDestructiveMigration() // Borra y reconstruye si cambia la versión
                    .build()
                INSTANCE = instance               // Guarda la instancia creada
                instance
            }
        }
    }
}
