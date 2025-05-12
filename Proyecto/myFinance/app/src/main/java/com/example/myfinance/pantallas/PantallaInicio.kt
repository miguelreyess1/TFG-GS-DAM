package com.example.myfinance.pantallas

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.Savings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.myfinance.componentes.BarraNavegacion
import com.example.myfinance.componentes.Header
import com.example.myfinance.data.local.AppDatabase
import com.example.myfinance.data.model.CategorySum
import com.example.myfinance.data.repository.TransaccionRepository
import com.example.myfinance.viewmodel.BalanceViewModel
import com.example.myfinance.viewmodel.BalanceViewModelFactory
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.sin

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaInicio(navController: NavController) {
    val context = LocalContext.current
    val db = AppDatabase.getDatabase(context)
    val repo = TransaccionRepository(db.transaccionDao())
    val viewModel: BalanceViewModel = viewModel(
        factory = BalanceViewModelFactory(repo)
    )

    val ingresos by viewModel.totalIngresos.collectAsState()
    val gastos   by viewModel.totalGastos.collectAsState()
    val balance  by viewModel.balanceTotal.collectAsState()

    val statsGastos   by viewModel.statsGastos.collectAsState(emptyList())
    val statsIngresos by viewModel.statsIngresos.collectAsState(emptyList())

    val bgColor    = Color(0xFFF5F5F5)
    val primary    = Color(0xFF6ADDB0)
    val textColor  = Color(0xFF004D40)

    Scaffold(
        topBar        = { Header() },
        bottomBar     = { BarraNavegacion(navController) },
        containerColor = MaterialTheme.colorScheme.background
    ) { padding ->
        val scrollState = rememberScrollState()
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .background(bgColor)
                .verticalScroll(scrollState)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            BalanceCard(balance, primary, Color.White)
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                MetricCard("Ingresos", ingresos, Icons.Default.ArrowDownward, Color(0xFF4CAF50), textColor, Modifier.weight(1f))
                MetricCard("Gastos",   gastos,   Icons.Default.ArrowUpward,   Color(0xFFE53935), textColor, Modifier.weight(1f))
                MetricCard("Ahorro",   balance,  Icons.Default.Savings,      Color(0xFF2196F3), textColor, Modifier.weight(1f))
            }

            PieChartCard(
                title = "Distribución de Gastos",
                data = statsGastos.map { it.copy(monto = abs(it.monto)) },
                modifier = Modifier.fillMaxWidth()
            )

            PieChartCard(
                title = "Distribución de Ingresos",
                data = statsIngresos,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun PieChartCard(
    title: String,
    data: List<CategorySum>,
    modifier: Modifier = Modifier,
    colors: List<Color> = listOf(
        Color(0xFF6ADDB0), // Primary
        Color(0xFF4D9DE0), // Azure
        Color(0xFF7E57C2), // Violet
        Color(0xFFFFB74D), // Orange
        Color(0xFFE57373), // Coral
        Color(0xFF81C784), // Green
        Color(0xFF64B5F6), // Sky
        Color(0xFFFFD54F)  // Amber
    ),
    showLabels: Boolean = true
) {
    val total by remember(data) {
        derivedStateOf { data.sumOf { it.monto.toDouble() }.toFloat() }
    }

    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF004D40)
                )
            )
            Spacer(Modifier.height(16.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(240.dp),
                contentAlignment = Alignment.Center
            ) {
                if (total <= 0f) {
                    Text("No hay datos disponibles", color = Color.Gray)
                    return@Box
                }

                Canvas(modifier = Modifier.fillMaxSize()) {
                    val radius = (size.minDimension / 2f) * 0.85f
                    val center = this.center
                    var startAngle = -90f

                    data.forEachIndexed { i, entry ->
                        val sweep = (entry.monto / total) * 360f
                        var color = colors[i % colors.size]

                        // Draw main arc
                        drawArc(
                            color = color,
                            startAngle = startAngle,
                            sweepAngle = sweep,
                            useCenter = true,
                            size = Size(radius * 2, radius * 2),
                            topLeft = Offset(center.x - radius, center.y - radius)
                        )

                        // Draw percentage labels
                        if (showLabels && sweep > 10f) {
                            val angleRad = Math.toRadians((startAngle + sweep / 2).toDouble())
                            val labelRadius = radius * 0.7
                            val x = center.x + (labelRadius * cos(angleRad)).toFloat()
                            val y = center.y + (labelRadius * sin(angleRad)).toFloat()


                            drawContext.canvas.nativeCanvas.drawText(
                                "${"%.0f".format(entry.monto / total * 100)}%",
                                x,
                                y,
                                android.graphics.Paint().apply {
                                    color =Color.White
                                    textAlign = android.graphics.Paint.Align.CENTER
                                    textSize = if (sweep > 30f) 32f else 24f
                                    isFakeBoldText = true
                                }
                            )
                        }

                        startAngle += sweep
                    }
                }
            }

            Spacer(Modifier.height(16.dp))

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                itemsIndexed(data) { i, entry ->
                    val color = colors[i % colors.size]
                    val percentage = (entry.monto / total) * 100

                    Row(
                        modifier = Modifier.padding(vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(14.dp)
                                .background(color, CircleShape)
                        )
                        Spacer(Modifier.width(8.dp))
                        Column {
                            Text(
                                text = entry.nombre,
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = Color(0xFF004D40),
                                    fontWeight = FontWeight.Medium
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun BalanceCard(balance: Double, backgroundColor: Color, textColor: Color) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(160.dp),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor, contentColor = textColor)
    ) {
        Column(
            Modifier.fillMaxSize().padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("BALANCE TOTAL", style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(8.dp))
            Text("%.2f €".format(balance), style = MaterialTheme.typography.displaySmall)
        }
    }
}

@Composable
private fun MetricCard(
    title: String,
    value: Double,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    iconTint: Color,
    textColor: Color,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.height(120.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White, contentColor = textColor)
    ) {
        Column(Modifier.fillMaxSize().padding(12.dp), verticalArrangement = Arrangement.SpaceBetween) {
            Icon(icon, contentDescription = title, tint = iconTint)
            Column {
                Text(title, style = MaterialTheme.typography.labelSmall)
                Text("%.2f €".format(value), style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            }
        }
    }
}
