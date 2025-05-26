package com.example.myfinance.pantallas

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myfinance.componentes.BarraNavegacion
import com.example.myfinance.componentes.Header
import kotlin.math.pow

/**
 * Pantalla de calculadora de interés compuesto.
 *
 * Muestra campos de entrada para balance inicial, depósito mensual,
 * tasa anual y número de años; y calcula el ahorro total,
 * desglozando el aporte inicial, depósitos y el interés generado.
 *
 * @param navController Controlador de navegación para la barra inferior.
 */
@Composable
fun PantallaCalculadora(navController: NavController) {
    // Estado de los inputs de usuario
    var balanceInicial by remember { mutableStateOf("1000") }    // Valor inicial por defecto
    var depositoMensual by remember { mutableStateOf("") }       // Aporte periódico
    var tasaAnual by remember { mutableStateOf("") }             // Tasa en %
    var numeroAnios by remember { mutableStateOf("") }           // Periodo en años

    // Control de visibilidad y resultados del cálculo
    var showResults by remember { mutableStateOf(false) }
    var resultadoInicial by remember { mutableDoubleStateOf(0.0) }
    var resultadoDepositos by remember { mutableDoubleStateOf(0.0) }
    var resultadoInteres by remember { mutableDoubleStateOf(0.0) }
    var totalAhorro by remember { mutableDoubleStateOf(0.0) }

    // Scaffold con AppBar y BottomBar
    Scaffold(
        topBar = { Header() },                                   // Cabecera común
        bottomBar = { BarraNavegacion(navController) },         // Barra de navegación
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        // Contenedor principal desplazable
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(paddingValues)                       // Respeta barras del Scaffold
                .verticalScroll(rememberScrollState())        // Habilita scroll
                .padding(16.dp),                              // Padding interno
            verticalArrangement = Arrangement.spacedBy(24.dp), // Espacio entre secciones
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Título de la pantalla
            Text(
                text = "Calculadora de Interés Compuesto",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )

            // Campos de entrada reutilizables con sufijos
            InputField(
                value = balanceInicial,
                label = "Balance inicial",
                suffix = "€"
            ) { balanceInicial = it }

            InputField(
                value = depositoMensual,
                label = "Depósito mensual",
                suffix = "€"
            ) { depositoMensual = it }

            InputField(
                value = tasaAnual,
                label = "Tasa de interés anual",
                suffix = "%"
            ) { tasaAnual = it }

            InputField(
                value = numeroAnios,
                label = "Número de años",
                suffix = "años"
            ) { numeroAnios = it }

            // Botones de acción: Calcular y Restablecer
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Button(
                    onClick = {
                        // Parseo de inputs y cálculo de interés compuesto
                        val p = balanceInicial.toDoubleOrNull() ?: 0.0
                        val m = depositoMensual.toDoubleOrNull() ?: 0.0
                        val r = (tasaAnual.toDoubleOrNull() ?: 0.0) / 100
                        val n = numeroAnios.toIntOrNull() ?: 0
                        val months = n * 12
                        val rm = if (months > 0) r / 12 else 0.0

                        // Valor futuro del aporte inicial
                        val acumuladoInicial = p * (1 + rm).pow(months)
                        // Aporte acumulado de depósitos mensuales
                        val acumuladoDepositos = if (rm != 0.0)
                            m * (((1 + rm).pow(months) - 1) / rm)
                        else
                            m * months.toDouble()

                        val sumaDepositos = m * months.toDouble()
                        val total = acumuladoInicial + acumuladoDepositos
                        val interes = total - p - sumaDepositos

                        // Actualización de estados para mostrar resultados
                        resultadoInicial = p
                        resultadoDepositos = sumaDepositos
                        resultadoInteres = interes
                        totalAhorro = total
                        showResults = true
                    },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    )
                ) {
                    Text("Calcular")
                }

                OutlinedButton(
                    onClick = {
                        // Reset de todos los campos y oculta resultados
                        balanceInicial = "1000"
                        depositoMensual = ""
                        tasaAnual = ""
                        numeroAnios = ""
                        showResults = false
                    },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Text("Restablecer")
                }
            }

            // Sección de resultados, visible tras el cálculo
            if (showResults) {
                val years = numeroAnios.toIntOrNull() ?: 0
                ResultadoSection(
                    initialBalance = resultadoInicial,
                    periodicDeposits = resultadoDepositos,
                    totalInterest = resultadoInteres,
                    totalAmount = totalAhorro,
                    years = years
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
/**
 * Campo de entrada de texto personalizado con estilo Material 3.
 *
 * Muestra un `OutlinedTextField` con etiqueta, sufijo como icono final
 * y esquinas redondeadas. Está pensado para entradas numéricas o textuales
 * de un solo renglón (por ejemplo, valores monetarios o porcentajes).
 *
 * @param value Texto actual del campo.
 * @param label Etiqueta flotante que describe el campo.
 * @param suffix Texto que se muestra al final (sufijo), por ejemplo unidad o símbolo.
 * @param onValueChange Callback que se invoca al modificar el texto.
 */
@Composable
private fun InputField(
    value: String,
    label: String,
    suffix: String,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        value = value,                       // Valor renderizado en el campo
        onValueChange = onValueChange,       // Actualiza el estado padre
        label = { Text(label) },             // Etiqueta descriptiva
        singleLine = true,                   // Sólo una línea de texto
        trailingIcon = {                     // Sufijo al final del campo
            Text(
                suffix,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        },
        shape = RoundedCornerShape(12.dp),   // Esquinas redondeadas personalizadas
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = MaterialTheme.colorScheme.primary,           // Color de borde al enfocar
            unfocusedBorderColor = MaterialTheme.colorScheme.onSurfaceVariant, // Color de borde en reposo
            cursorColor = MaterialTheme.colorScheme.primary                   // Color del cursor
        ),
        modifier = Modifier.fillMaxWidth()    // Ocupa el ancho disponible
    )
}

/**
 * Sección que muestra los resultados del cálculo de interés compuesto
 * en una tarjeta estilizada, con leyenda y gráfico de pastel.
 *
 * Desglosa el balance inicial, los depósitos periódicos, el interés total
 * y el monto total ahorrado en un periodo dado.
 *
 * @param initialBalance Monto inicial invertido.
 * @param periodicDeposits Suma total de todos los depósitos periódicos.
 * @param totalInterest Interés acumulado generado.
 * @param totalAmount Monto final total (inversión + depósitos + interés).
 * @param years Número de años para el cálculo (se usa para el texto descriptivo).
 */
@Composable
private fun ResultadoSection(
    initialBalance: Double,
    periodicDeposits: Double,
    totalInterest: Double,
    totalAmount: Double,
    years: Int
) {
    // Colores para la leyenda y el gráfico de pastel
    val colors = listOf(
        MaterialTheme.colorScheme.primary,    // Balance inicial
        MaterialTheme.colorScheme.secondary,  // Depósitos periódicos
        MaterialTheme.colorScheme.tertiary    // Interés total
    )

    // Tarjeta contenedora de los resultados
    Card(
        shape = RoundedCornerShape(16.dp),                    // Esquinas redondeadas
        elevation = CardDefaults.cardElevation(8.dp),         // Elevación para sombra
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface // Color de fondo
        ),
        modifier = Modifier.fillMaxWidth()                    // Ocupa todo el ancho
    ) {
        Column(
            modifier = Modifier.padding(24.dp),               // Espaciado interno
            verticalArrangement = Arrangement.spacedBy(16.dp) // Separación vertical entre ítems
        ) {
            // Título de sección
            Text(
                text = "Puedes ahorrar",
                style = MaterialTheme.typography.titleMedium.copy(fontSize = 20.sp),
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
            // Monto total destacado
            Text(
                text = "%.2f €".format(totalAmount),
                style = MaterialTheme.typography.displaySmall,
                color = MaterialTheme.colorScheme.primary
            )
            // Descripción del ahorro mensual
            Text(
                text = "Ahorro de %.2f € mensual durante $years años"
                    .format(periodicDeposits / (years * 12)),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            // Separador horizontal
            HorizontalDivider(
                thickness = 1.dp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            // Fila: Leyenda y valor del balance inicial
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    // Indicador de color para balance inicial
                    Box(
                        modifier = Modifier
                            .size(12.dp)
                            .background(colors[0], shape = RoundedCornerShape(2.dp))
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Balance inicial:",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
                Text(
                    text = "%.2f €".format(initialBalance),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }

            // Fila: Leyenda y valor de depósitos periódicos
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    // Indicador de color para depósitos periódicos
                    Box(
                        modifier = Modifier
                            .size(12.dp)
                            .background(colors[1], shape = RoundedCornerShape(2.dp))
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Depósitos periódicos:",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
                Text(
                    text = "%.2f €".format(periodicDeposits),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }

            // Fila: Leyenda y valor del interés total
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    // Indicador de color para interés total
                    Box(
                        modifier = Modifier
                            .size(12.dp)
                            .background(colors[2], shape = RoundedCornerShape(2.dp))
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Interés total:",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
                Text(
                    text = "%.2f €".format(totalInterest),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }

            // Gráfico de pastel con los tres valores
            PieChart(
                data = listOf(initialBalance, periodicDeposits, totalInterest),
                colors = colors
            )
        }
    }
}

/**
 * Composable que dibuja un gráfico de pastel a partir de una lista de valores numéricos.
 *
 * Cada sector del pastel representa la proporción de su valor respecto al total.
 *
 * @param data Lista de valores numéricos que definen el tamaño de cada sector.
 * @param colors Lista de colores correspondientes a cada sector del gráfico.
 */
@Composable
private fun PieChart(data: List<Double>, colors: List<Color>) {
    // Calcula la suma total; garantiza al menos 1.0 para evitar división por cero
    val total = data.sum().takeIf { it > 0 } ?: 1.0

    // Canvas para dibujar el gráfico, con tamaño fijo y padding interno
    Canvas(
        modifier = Modifier
            .size(200.dp)   // Dimensión cuadrada del gráfico
            .padding(8.dp)  // Espacio interior alrededor del dibujo
    ) {
        // Ángulo inicial apuntando hacia arriba (–90° corresponde a las 12 horas)
        var startAngle = -90f

        // Recorre cada valor para dibujar su sector proporcional
        data.forEachIndexed { index, value ->
            // Calcula ángulo de barrido según proporción del total
            val sweep = (value / total * 360.0).toFloat()

            // Dibuja un arco desde startAngle con barrido sweep usando el color correspondiente
            drawArc(color = colors[index], startAngle = startAngle, sweepAngle = sweep, useCenter = true)

            // Actualiza el ángulo inicial para el siguiente sector
            startAngle += sweep
        }
    }
}
