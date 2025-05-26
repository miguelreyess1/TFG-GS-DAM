package com.example.myfinance.data.model

/**
 * Modelo que representa la suma de montos agrupados por categoría.
 *
 * Se utiliza principalmente para mostrar estadísticas o gráficos donde
 * cada categoría tiene un nombre y un monto total asociado.
 *
 * @property nombre Nombre de la categoría (ej. "Alimentación", "Transporte").
 * @property monto Suma total de los montos (valor absoluto) para la categoría.
 */
data class CategorySum(
    val nombre: String, // Nombre de la categoría
    val monto: Float    // Monto total acumulado para esta categoría
)
