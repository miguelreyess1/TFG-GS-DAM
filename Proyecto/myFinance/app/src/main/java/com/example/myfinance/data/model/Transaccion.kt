package com.example.myfinance.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.Date

/**
 * Representa una transacción financiera en la aplicación.
 *
 * Cada transacción está vinculada a una categoría y contiene información
 * sobre su tipo, monto, descripción y fecha.
 *
 * @property id Identificador único de la transacción. Se genera automáticamente.
 * @property tipo Tipo de transacción ("ingreso" o "gasto").
 * @property monto Valor numérico de la transacción.
 * @property descripcion Descripción breve de la transacción.
 * @property fecha Fecha en que ocurrió la transacción.
 * @property categoriaId ID de la categoría a la que pertenece la transacción.
 */
@Entity(
    tableName = "transacciones",
    foreignKeys = [
        ForeignKey(
            entity = Categoria::class,
            parentColumns = ["id"],
            childColumns = ["categoriaId"],
            onDelete = ForeignKey.RESTRICT // Evita borrado de categoría si tiene transacciones
        )
    ]
)
data class Transaccion(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,           // ID autogenerado por Room al insertar

    val tipo: String,          // "ingreso" o "gasto"

    val monto: Double,         // Monto de la transacción

    val descripcion: String,   // Detalle o nota de la transacción

    val fecha: Date,           // Fecha en que se realizó la transacción

    val categoriaId: Int       // FK a Categoria.id
)
