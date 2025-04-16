package com.example.myfinance.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "huchas")
data class Hucha(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nombre: String
)
