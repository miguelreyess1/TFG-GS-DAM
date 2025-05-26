package com.example.myfinance.data.local

import androidx.room.TypeConverter
import java.util.Date

/**
 * Proporciona conversores de tipos para Room, permitiendo almacenar
 * objetos [Date] como valores primitivos (Long) en la base de datos.
 */
class Converters {

    /**
     * Convierte un valor de timestamp (milisegundos desde Unix epoch) a [Date].
     *
     * @param value El timestamp en milisegundos. Puede ser null.
     * @return Un objeto [Date] correspondiente al timestamp, o null si el valor es null.
     */
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? =
        // Si value no es null, crea un Date; si es null, retorna null
        value?.let { Date(it) }

    /**
     * Convierte un objeto [Date] a su representación en timestamp
     * (milisegundos desde Unix epoch).
     *
     * @param date Objeto [Date] a convertir. Puede ser null.
     * @return El timestamp en milisegundos de la fecha, o null si la fecha es null.
     */
    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? =
        // Si date no es null, devuelve su propiedad time; si es null, retorna null
        date?.time
}
