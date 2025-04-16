package com.example.myfinance.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import kotlin.math.min
import kotlin.math.pow

data class YearResult(
    val year: Int,
    val totalDepositedSoFar: Double,
    val totalAmountEndOfYear: Double,
    val interestEarnedSoFar: Double
)

@Composable
fun PantallaCalculadora() {
    var balanceInicialInput by remember { mutableStateOf("") }
    var depositoPeriodicoInput by remember { mutableStateOf("") }
    var tasaInput by remember { mutableStateOf("") }
    var duracionInput by remember { mutableStateOf("") }

    val frequencyOptions = listOf("Mensual", "Anual")
    var selectedFrequency by remember { mutableStateOf("Mensual") }

    var resultados by remember { mutableStateOf<List<YearResult>>(emptyList()) }

    fun calcularEvolucion() {
        val balanceInicial = balanceInicialInput.toDoubleOrNull() ?: 0.0
        val depositoPeriodico = depositoPeriodicoInput.toDoubleOrNull() ?: 0.0
        val tasaAnual = tasaInput.toDoubleOrNull() ?: 0.0
        val duracion = duracionInput.toIntOrNull() ?: 0

        if (duracion <= 0) {
            resultados = emptyList()
            return
        }

        val r = tasaAnual / 100.0
        val n = if (selectedFrequency == "Mensual") 12 else 1

        val tempResultados = mutableListOf<YearResult>()

        var capitalAcumulado = 0.0
        var totalDepositado = 0.0

        capitalAcumulado += balanceInicial
        totalDepositado += balanceInicial

        for (year in 1..duracion) {
            val factor = (1 + r / n).pow(n)
            capitalAcumulado *= factor

            if (depositoPeriodico > 0.0) {
                val amountFromDeposits = if (r != 0.0) {
                    depositoPeriodico * ((factor - 1) / (r / n))
                } else {
                    depositoPeriodico * n
                }
                capitalAcumulado += amountFromDeposits
                totalDepositado += depositoPeriodico * n
            }
            val interestSoFar = capitalAcumulado - totalDepositado

            tempResultados.add(
                YearResult(
                    year = year,
                    totalDepositedSoFar = totalDepositado,
                    totalAmountEndOfYear = capitalAcumulado,
                    interestEarnedSoFar = interestSoFar
                )
            )
        }
        resultados = tempResultados
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Text(
            text = "Calculadora de Interés Compuesto",
            style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold)
        )
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = balanceInicialInput,
            onValueChange = { balanceInicialInput = it },
            label = { Text("Balance Inicial") },
            placeholder = { Text("Ej.: 1000") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = depositoPeriodicoInput,
            onValueChange = { depositoPeriodicoInput = it },
            label = { Text("Depósito Periódico") },
            placeholder = { Text("Ej.: 100") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("Frecuencia: ", modifier = Modifier.padding(end = 8.dp))
            frequencyOptions.forEach { option ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(end = 16.dp)
                ) {
                    RadioButton(
                        selected = (selectedFrequency == option),
                        onClick = { selectedFrequency = option }
                    )
                    Text(text = option)
                }
            }
        }
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = tasaInput,
            onValueChange = { tasaInput = it },
            label = { Text("Tasa Interés Anual (%)") },
            placeholder = { Text("Ej.: 5") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = duracionInput,
            onValueChange = { duracionInput = it },
            label = { Text("Duración (años)") },
            placeholder = { Text("Ej.: 10") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { calcularEvolucion() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Calcular")
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (resultados.isNotEmpty()) {
            val ultimo = resultados.last()
            val totalDepositado = ultimo.totalDepositedSoFar
            val montoFinal = ultimo.totalAmountEndOfYear
            val intereses = ultimo.interestEarnedSoFar

            Text(
                text = "Puedes ahorrar ${"%.2f".format(montoFinal)} €",
                style = MaterialTheme.typography.headlineSmall
            )
            Text(
                text = "Aportando ${depositoPeriodicoInput.ifEmpty { "0" }} € " +
                        "${selectedFrequency.lowercase()} durante ${duracionInput.ifEmpty { "0" }} años."
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Aportado en total: ${"%.2f".format(totalDepositado)} €",
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = "Intereses ganados: ${"%.2f".format(intereses)} €",
                style = MaterialTheme.typography.bodyLarge
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Evolución Anual:",
                style = MaterialTheme.typography.titleMedium
            )
            BarChart(
                data = resultados.map { it.totalAmountEndOfYear },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .padding(vertical = 8.dp)
            )

            Text(
                text = "Distribución (Último Año):",
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
            PieChart(
                aporte = totalDepositado,
                interes = intereses,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )
        }
    }
}

@Composable
fun BarChart(
    data: List<Double>,
    modifier: Modifier = Modifier,
    barColor: Color = Color(0xFF6200EE)
) {
    val maxValue = data.maxOrNull()?.toFloat() ?: 0f // Convertido a Float
    if (maxValue == 0f) {
        Box(modifier = modifier, contentAlignment = Alignment.Center) {
            Text(text = "Sin datos")
        }
        return
    }
    val floatData = data.map { it.toFloat() }

    Canvas(modifier = modifier.background(Color.White)) {
        val barWidth = size.width / (data.size * 2)
        val maxHeight = size.height

        floatData.forEachIndexed { index, value ->
            val barHeight = (value / maxValue) * maxHeight
            val barX = barWidth * (index * 2 + 0.5f)
            val barY = size.height - barHeight

            drawRoundRect(
                color = barColor,
                topLeft = Offset(barX, barY),
                size = androidx.compose.ui.geometry.Size(barWidth, barHeight),
                cornerRadius = androidx.compose.ui.geometry.CornerRadius(8f, 8f)
            )
        }
    }
}

@Composable
fun PieChart(
    aporte: Double,
    interes: Double,
    modifier: Modifier = Modifier
) {
    val total = aporte + interes
    if (total <= 0.0) {
        Box(modifier = modifier, contentAlignment = Alignment.Center) {
            Text(text = "Sin datos")
        }
        return
    }
    val aporteAng = (aporte / total * 360).toFloat()
    val interesAng = (interes / total * 360).toFloat()

    Canvas(modifier = modifier.background(Color.White)) {
        val canvasSize = min(size.width, size.height)
        val radius = canvasSize / 2f
        val centerOffset = Offset(size.width / 2f, size.height / 2f)

        drawArc(
            color = Color(0xFF03DAC5),
            startAngle = 0f,
            sweepAngle = aporteAng,
            useCenter = true,
            topLeft = Offset(centerOffset.x - radius, centerOffset.y - radius),
            size = androidx.compose.ui.geometry.Size(radius * 2, radius * 2)
        )

        drawArc(
            color = Color(0xFFFFBB86),
            startAngle = aporteAng,
            sweepAngle = interesAng,
            useCenter = true,
            topLeft = Offset(centerOffset.x - radius, centerOffset.y - radius),
            size = androidx.compose.ui.geometry.Size(radius * 2, radius * 2)
        )

        drawArc(
            color = Color.Gray,
            startAngle = 0f,
            sweepAngle = 360f,
            useCenter = false,
            topLeft = Offset(centerOffset.x - radius, centerOffset.y - radius),
            size = androidx.compose.ui.geometry.Size(radius * 2, radius * 2),
            style = Stroke(width = 2f)
        )
    }
}