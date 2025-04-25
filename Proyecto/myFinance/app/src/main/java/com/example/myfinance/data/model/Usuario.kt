package com.example.myfinance.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "usuario")
data class Usuario(
    @PrimaryKey val id: Int = 0,
    val nombre: String,
    val imageUri: String? = null
)
