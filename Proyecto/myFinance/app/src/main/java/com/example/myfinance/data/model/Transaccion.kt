package com.example.myfinance.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.*

@Entity(
    tableName = "transacciones",
    foreignKeys = [
        ForeignKey(
            entity = Categoria::class,
            parentColumns = ["id"],
            childColumns = ["categoriaId"],
            onDelete = ForeignKey.RESTRICT
        )
    ]
)
data class Transaccion(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val tipo: String,
    val monto: Double,
    val descripcion: String,
    val fecha: Date,
    val categoriaId: Int
)
