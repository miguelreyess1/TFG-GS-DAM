package com.example.myfinance.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Representa una categoría de transacción en la aplicación.
 *
 * Las categorías permiten clasificar las transacciones como ingresos o gastos,
 * y agruparlas por nombre.
 *
 * @property id Identificador único de la categoría. Se genera automáticamente.
 * @property nombre Nombre descriptivo de la categoría (por ejemplo, "Alimentación").
 * @property tipo Tipo de categoría: suele ser "ingreso" o "gasto".
 */
@Entity(tableName = "categorias")
data class Categoria(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,      // ID autogenerado por Room al insertar la categoría

    val nombre: String,   // Nombre visible de la categoría

    val tipo: String      // Tipo para filtrar: "ingreso" o "gasto"
)
