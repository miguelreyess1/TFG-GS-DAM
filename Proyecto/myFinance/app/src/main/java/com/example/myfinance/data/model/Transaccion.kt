package com.example.myfinance.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "transacciones")
data class Transaccion(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val tipo: String,
    val monto: Double,
    val descripcion: String,
    val fecha: Date,
    val categoriaId: Int
)