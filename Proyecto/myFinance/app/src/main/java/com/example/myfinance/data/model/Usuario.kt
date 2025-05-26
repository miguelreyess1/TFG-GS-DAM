package com.example.myfinance.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Entidad que representa los datos del usuario de la aplicación.
 *
 * Se asume un único registro con `id = 0` que almacena la información
 * del usuario activo.
 *
 * @property id Identificador fijo del usuario (por defecto 0).
 * @property nombre Nombre completo o alias del usuario.
 * @property imageUri URI opcional de la imagen de perfil del usuario.
 */
@Entity(tableName = "usuario")
data class Usuario(
    @PrimaryKey
    val id: Int = 0,          // ID único, siempre 0 para el usuario principal

    val nombre: String,       // Nombre o alias visible del usuario

    val imageUri: String? = null // URI de la imagen de perfil; null si no hay
)
