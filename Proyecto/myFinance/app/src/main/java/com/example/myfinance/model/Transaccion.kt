package com.example.myfinance.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "transacciones",
    foreignKeys = [
        ForeignKey(
            entity = Categoria::class,
            parentColumns = ["id"],
            childColumns = ["categoriaId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Hucha::class,
            parentColumns = ["id"],
            childColumns = ["huchaId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Transaccion(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val fecha: Long,            // Se almacena en formato timestamp (milisegundos)
    val cantidad: Double,       // Monto de la transacción
    val tipo: String,           // "ingreso" o "gasto"
    val descripcion: String?,
    val categoriaId: Int,       // Relación con la categoría
    val huchaId: Int            // Relación con la hucha
)
