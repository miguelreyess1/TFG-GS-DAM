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

@Composable
fun PantallaCalculadora(navController: NavController) {
    var balanceInicial by remember { mutableStateOf("1000") }
    var depositoMensual by remember { mutableStateOf("") }
    var tasaAnual by remember { mutableStateOf("") }
    var numeroAnios by remember { mutableStateOf("") }
    var showResults by remember { mutableStateOf(false) }
    var resultadoInicial by remember { mutableDoubleStateOf(0.0) }
    var resultadoDepositos by remember { mutableDoubleStateOf(0.0) }
    var resultadoInteres by remember { mutableDoubleStateOf(0.0) }
    var totalAhorro by remember { mutableDoubleStateOf(0.0) }

    Scaffold(
        topBar = { Header() },
        bottomBar = { BarraNavegacion(navController) },
        containerColor = Color(0xFFF5F5F5)
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFFAFAFA))
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Calculadora de Interés Compuesto",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            InputField(value = balanceInicial, label = "Balance inicial", suffix = "€") { balanceInicial = it }
            InputField(value = depositoMensual, label = "Depósito mensual por mes", suffix = "€") { depositoMensual = it }
            InputField(value = tasaAnual, label = "Tasa de interés anual", suffix = "%") { tasaAnual = it }
            InputField(value = numeroAnios, label = "Número de años", suffix = "años") { numeroAnios = it }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Button(
                    onClick = {
                        val p = balanceInicial.toDoubleOrNull() ?: 0.0
                        val m = depositoMensual.toDoubleOrNull() ?: 0.0
                        val r = (tasaAnual.toDoubleOrNull() ?: 0.0) / 100
                        val n = numeroAnios.toIntOrNull() ?: 0
                        val months = n * 12
                        val rm = if (n > 0) r / 12 else 0.0
                        val acumuladoInicial = p * (1 + rm).pow(months)
                        val acumuladoDepositos = if (rm != 0.0)
                            m * (((1 + rm).pow(months) - 1) / rm)
                        else
                            m * months.toDouble()
                        val sumaDepositos = m * months.toDouble()
                        val total = acumuladoInicial + acumuladoDepositos
                        val interes = total - p - sumaDepositos

                        resultadoInicial = p
                        resultadoDepositos = sumaDepositos
                        resultadoInteres = interes
                        totalAhorro = total
                        showResults = true
                    },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF62DBB1))
                ) {
                    Text("Calcular")
                }
                OutlinedButton(
                    onClick = {
                        balanceInicial = "1000"
                        depositoMensual = ""
                        tasaAnual = ""
                        numeroAnios = ""
                        showResults = false
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Restablecer", color = Color(0xFF62DBB1))
                }
            }

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

@Composable
private fun InputField(
    value: String,
    label: String,
    suffix: String,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        singleLine = true,
        trailingIcon = { Text(suffix, color = Color.Gray) },
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp)
    )
}

@Composable
private fun ResultadoSection(
    initialBalance: Double,
    periodicDeposits: Double,
    totalInterest: Double,
    totalAmount: Double,
    years: Int
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Puedes ahorrar",
                style = MaterialTheme.typography.titleMedium.copy(fontSize = 20.sp),
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "%.2f €".format(totalAmount),
                style = MaterialTheme.typography.displaySmall,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = "Ahorro de %.2f € mensual durante $years años".format(periodicDeposits / (years * 12)),
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )
            HorizontalDivider(thickness = 1.dp, color = Color(0xFFDDDDDD))
            DataRow("Balance inicial:", initialBalance, Color(0xFF9C27B0))
            DataRow("Depósitos periódicos:", periodicDeposits, Color(0xFF2196F3))
            DataRow("Interés total:", totalInterest, Color(0xFF4CAF50))
            PieChart(
                data = listOf(initialBalance, periodicDeposits, totalInterest),
                colors = listOf(
                    Color(0xFF9C27B0),
                    Color(0xFF2196F3),
                    Color(0xFF4CAF50)
                )
            )
        }
    }
}

@Composable
private fun DataRow(label: String, value: Double, color: Color) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, color = color, style = MaterialTheme.typography.bodyMedium)
        Text("%.2f €".format(value), style = MaterialTheme.typography.bodyMedium)
    }
}

@Composable
private fun PieChart(data: List<Double>, colors: List<Color>) {
    val total = data.sum().takeIf { it > 0 } ?: 1.0
    Canvas(
        modifier = Modifier
            .size(200.dp)
            .padding(8.dp)
    ) {
        var startAngle = -90f
        data.forEachIndexed { index, value ->
            val sweep = (value / total * 360.0).toFloat()
            drawArc(
                color = colors[index],
                startAngle = startAngle,
                sweepAngle = sweep,
                useCenter = true
            )
            startAngle += sweep
        }
    }
}
