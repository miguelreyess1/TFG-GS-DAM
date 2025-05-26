package com.example.myfinance.pantallas

import android.graphics.Paint
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
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.myfinance.componentes.BarraNavegacion
import com.example.myfinance.componentes.Header
import com.example.myfinance.data.local.AppDatabase
import com.example.myfinance.data.model.CategorySum
import com.example.myfinance.data.repository.TransaccionRepository
import com.example.myfinance.theme.ChartColors
import com.example.myfinance.viewmodel.BalanceViewModel
import com.example.myfinance.viewmodel.BalanceViewModelFactory
import kotlin.math.cos
import kotlin.math.sin

/**
 * Pantalla de inicio que muestra el balance total, métricas clave (ingresos,
 * gastos, ahorro) y gráficos de distribución de categorías.
 *
 * Utiliza un ViewModel para obtener datos reactivos de la base de datos.
 *
 * @param navController Controlador de navegación para la barra inferior.
 */
@Composable
fun PantallaInicio(navController: NavController) {
    // Contexto para inicializar la base de datos
    val context = LocalContext.current
    val db = AppDatabase.getDatabase(context)

    // Repositorio de transacciones y ViewModel asociado
    val repo = TransaccionRepository(db.transaccionDao())
    val viewModel: BalanceViewModel = viewModel(
        factory = BalanceViewModelFactory(repo)
    )

    // Estados que recolectan los flujos del ViewModel
    val ingresos by viewModel.totalIngresos.collectAsState()
    val gastos by viewModel.totalGastos.collectAsState()
    val balance by viewModel.balanceTotal.collectAsState()
    val statsGastos by viewModel.statsGastos.collectAsState(initial = emptyList())
    val statsIngresos by viewModel.statsIngresos.collectAsState(initial = emptyList())

    // Colores del tema
    val cs = MaterialTheme.colorScheme
    val background = cs.background

    // Estructura principal con encabezado, contenido y barra inferior
    Scaffold(
        topBar = { Header() },                         // Header con logo y título
        bottomBar = { BarraNavegacion(navController) },// Barra de navegación inferior
        containerColor = background
    ) { padding ->
        val scrollState = rememberScrollState()
        Column(
            modifier = Modifier
                .padding(padding)                     // Ajusta espacio por Scaffold
                .fillMaxSize()
                .background(background)
                .verticalScroll(scrollState)          // Habilita desplazamiento
                .padding(16.dp),                      // Padding interno general
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            // Tarjeta de balance total
            BalanceCard(balance, cs.surface, cs.onBackground)

            // Fila de métricas: ingresos, gastos y ahorro
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                MetricCard(
                    title    = "Ingresos",
                    value    = ingresos,
                    icon     = Icons.Default.ArrowDownward,
                    iconTint = cs.secondary,
                    textColor= cs.onBackground,
                    modifier = Modifier.weight(1f)
                )
                MetricCard(
                    title    = "Gastos",
                    value    = gastos,
                    icon     = Icons.Default.ArrowUpward,
                    iconTint = cs.error,
                    textColor= cs.onBackground,
                    modifier = Modifier.weight(1f)
                )
                MetricCard(
                    title    = "Ahorro",
                    value    = balance,
                    icon     = Icons.Default.Savings,
                    iconTint = cs.primary,
                    textColor= cs.onBackground,
                    modifier = Modifier.weight(1f)
                )
            }

            // Gráficos de pastel para distribución de gastos e ingresos
            PieChartCard(
                title   = "Distribución de Gastos",
                data    = statsGastos.map { it.copy(monto = kotlin.math.abs(it.monto)) },
                modifier= Modifier.fillMaxWidth(),
                colors  = ChartColors
            )
            PieChartCard(
                title   = "Distribución de Ingresos",
                data    = statsIngresos,
                modifier= Modifier.fillMaxWidth(),
                colors  = ChartColors
            )
        }
    }
}

/**
 * Composable que muestra el balance total en una tarjeta destacada.
 *
 * @param balance Monto total calculado.
 * @param backgroundColor Color de fondo de la tarjeta.
 * @param textColor Color del texto y contenido de la tarjeta.
 */
@Composable
private fun BalanceCard(
    balance: Double,
    backgroundColor: Color,
    textColor: Color
) {
    // Tarjeta con tamaño fijo y esquinas redondeadas
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(160.dp),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = backgroundColor,
            contentColor = textColor
        )
    ) {
        // Columna centrada para texto
        Column(
            Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "BALANCE TOTAL",
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "%.2f €".format(balance),
                style = MaterialTheme.typography.displaySmall
            )
        }
    }
}

/**
 * Composable que muestra un gráfico de pastel con título y leyenda horizontal.
 *
 * Despliega los valores de [data] como porcentajes del total con colores en [colors].
 *
 * @param title      Título de la sección.
 * @param data       Lista de [CategorySum] (nombre y monto) para el gráfico.
 * @param modifier   Modificador para el layout externo.
 * @param colors     Lista de colores para los sectores.
 * @param showLabels Si es true, muestra porcentajes dentro de sectores mayores a 10°.
 */
@Composable
private fun PieChartCard(
    title: String,
    data: List<CategorySum>,
    modifier: Modifier = Modifier,
    colors: List<Color>,
    showLabels: Boolean = true
) {
    // Suma total de todos los montos (evita división por cero)
    val total by remember(data) {
        derivedStateOf { data.sumOf { it.monto.toDouble() }.toFloat() }
    }

    // Contenedor de tarjeta con título y gráfico
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor   = MaterialTheme.colorScheme.onSurface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Título de sección
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onBackground
                )
            )
            Spacer(Modifier.height(16.dp))

            // Box para centrar el Canvas
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(240.dp),
                contentAlignment = Alignment.Center
            ) {
                if (total <= 0f) {
                    // Mensaje cuando no hay datos
                    Text(
                        text = "No hay datos disponibles",
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    return@Box
                }

                // Dibujo del gráfico en un Canvas
                Canvas(modifier = Modifier.fillMaxSize()) {
                    val radius = (size.minDimension / 2f) * 0.85f
                    val center = this.center
                    var startAngle = -90f

                    data.forEachIndexed { index, entry ->
                        // Ángulo de barrido proporcional al monto
                        val sweep = (entry.monto / total) * 360f
                        val sliceColor = colors[index % colors.size]

                        // Dibuja el arco del sector
                        drawArc(
                            color      = sliceColor,
                            startAngle = startAngle,
                            sweepAngle = sweep,
                            useCenter  = true,
                            size       = Size(radius * 2, radius * 2),
                            topLeft    = Offset(center.x - radius, center.y - radius)
                        )

                        if (showLabels && sweep > 10f) {
                            // Calcula posición del texto de porcentaje
                            val midAngle = Math.toRadians((startAngle + sweep / 2).toDouble())
                            val labelRadius = radius * 0.7f
                            val x = center.x + (labelRadius * cos(midAngle)).toFloat()
                            val y = center.y + (labelRadius * sin(midAngle)).toFloat()

                            // Dibuja porcentaje con Canvas nativo
                            drawContext.canvas.nativeCanvas.drawText(
                                "${"%.0f".format(entry.monto / total * 100)}%",
                                x, y,
                                Paint().apply {
                                    color         = android.graphics.Color.WHITE
                                    textAlign     = Paint.Align.CENTER
                                    textSize      = if (sweep > 30f) 32f else 24f
                                    isFakeBoldText = true
                                }
                            )
                        }

                        startAngle += sweep
                    }
                }
            }

            Spacer(Modifier.height(16.dp))

            // Leyenda horizontal con nombre de cada categoría
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                itemsIndexed(data) { index, entry ->
                    val dotColor = colors[index % colors.size]
                    Row(
                        modifier = Modifier.padding(vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(14.dp)
                                .background(dotColor, CircleShape)
                        )
                        Spacer(Modifier.width(8.dp))
                        Text(
                            text = entry.nombre,
                            style = MaterialTheme.typography.labelSmall.copy(
                                color      = MaterialTheme.colorScheme.onBackground,
                                fontWeight = FontWeight.Medium
                            )
                        )
                    }
                }
            }
        }
    }
}

/**
 * Composable que muestra una métrica clave (título, valor e ícono) en una tarjeta compacta.
 *
 * @param title     Texto descriptivo de la métrica.
 * @param value     Valor numérico de la métrica.
 * @param icon      Icono representativo.
 * @param iconTint  Color del icono.
 * @param textColor Color del texto.
 * @param modifier  Modificador para definir anchura/alto.
 */
@Composable
private fun MetricCard(
    title: String,
    value: Double,
    icon: ImageVector,
    iconTint: Color,
    textColor: Color,
    modifier: Modifier = Modifier
) {
    // Tarjeta con altura fija y esquinas suaves
    Card(
        modifier = modifier.height(120.dp),
        shape    = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors   = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor   = textColor
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Icono grande en la parte superior
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = iconTint
            )
            // Título y valor en columna inferior
            Column {
                Text(
                    text  = title,
                    style = MaterialTheme.typography.labelSmall,
                    color = textColor
                )
                Text(
                    text       = "%.2f €".format(value),
                    style      = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color      = textColor
                )
            }
        }
    }
}