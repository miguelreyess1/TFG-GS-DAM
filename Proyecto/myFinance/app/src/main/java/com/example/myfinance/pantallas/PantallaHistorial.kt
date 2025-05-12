package com.example.myfinance.pantallas

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.myfinance.componentes.BarraNavegacion
import com.example.myfinance.componentes.Header
import com.example.myfinance.data.local.AppDatabase
import com.example.myfinance.data.model.Categoria
import com.example.myfinance.data.model.Transaccion
import com.example.myfinance.data.repository.CategoriaRepository
import com.example.myfinance.data.repository.TransaccionRepository
import com.example.myfinance.viewmodel.TransaccionViewModel
import com.example.myfinance.viewmodel.TransaccionViewModelFactory
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun PantallaHistorial(navController: NavController) {
    val context = LocalContext.current
    val appDatabase = AppDatabase.getDatabase(context)

    val transaccionRepository = TransaccionRepository(appDatabase.transaccionDao())
    val categoriaRepository = CategoriaRepository(appDatabase.categoriaDao())

    val transaccionViewModel = viewModel<TransaccionViewModel>(
        factory = TransaccionViewModelFactory(transaccionRepository)
    )

    val transacciones by transaccionViewModel.transacciones.collectAsState(initial = emptyList())
    val categorias by produceState(initialValue = emptyList<Categoria>()) {
        value = categoriaRepository.getAll()
    }
    var selectedFilter by remember { mutableStateOf("TODOS") }

    Scaffold(
        topBar = { Header() },
        bottomBar = { BarraNavegacion(navController) }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .background(Color(0xFFFAFAFA))
        ) {
            // Filtros
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Historial de Transacciones",
                    style = MaterialTheme.typography.titleLarge.copy(
                        color = Color(0xFF004D40)
                    ),
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    listOf("TODOS", "INGRESO", "GASTO").forEach { filter ->
                        FilterChip(
                            selected = selectedFilter == filter,
                            onClick = { selectedFilter = filter },
                            label = { Text(filter) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = Color(0xFF6ADDB0),
                                selectedLabelColor = Color.White
                            )
                        )
                    }
                }
            }

            TransactionList(
                transacciones = filterTransactions(transacciones, selectedFilter),
                categorias = categorias
            )
        }
    }
}

@Composable
private fun TransactionList(transacciones: List<Transaccion>, categorias: List<Categoria>) {
    val groupedTransactions = transacciones.groupBy {
        SimpleDateFormat("dd MMM yyyy", Locale("es", "ES")).format(it.fecha)
    }

    LazyColumn(
        modifier = Modifier.padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        groupedTransactions.forEach { (fecha, transaccionesDia) ->
            item {
                Text(
                    text = fecha,
                    style = MaterialTheme.typography.titleSmall,
                    color = Color.Gray,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }

            items(transaccionesDia) { transaccion ->
                TransactionItem(
                    transaccion = transaccion,
                    categoria = categorias.find { it.id == transaccion.categoriaId }
                )
            }
        }
    }
}

@Composable
private fun TransactionItem(transaccion: Transaccion, categoria: Categoria?) {
    val tipoColor = if (transaccion.tipo.uppercase() == "INGRESO") Color(0xFF4CAF50) else Color(0xFFE53935)
    val icono = remember(categoria?.nombre) { iconoParaCategoria(categoria?.nombre ?: "") }
    val formatoHora = remember { SimpleDateFormat("HH:mm", Locale.getDefault()) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icono,
                contentDescription = null,
                modifier = Modifier.size(32.dp),
                tint = Color(0xFF004D40)
            )

            Spacer(Modifier.width(16.dp))

            Column(Modifier.weight(1f)) {
                Text(
                    text = transaccion.descripcion.ifEmpty { categoria?.nombre ?: "Sin categoría" },
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Medium)
                )
                Text(
                    text = formatoHora.format(transaccion.fecha),
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.Gray
                )
            }

            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = "${if (transaccion.tipo == "GASTO") "-" else ""}${"%.2f€".format(transaccion.monto)}",
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = tipoColor,
                        fontWeight = FontWeight.Bold)
                )
                Text(
                    text = transaccion.tipo.replaceFirstChar { it.uppercase() },
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = tipoColor)
                )
            }
        }
    }
}

private fun iconoParaCategoria(nombre: String): ImageVector = when (nombre) {
    "Alimentación" -> Icons.Filled.Fastfood
    "Transporte" -> Icons.Filled.DirectionsCar
    "Hogar" -> Icons.Filled.Home
    "Compras" -> Icons.Filled.ShoppingBag
    "Ocio" -> Icons.Filled.Movie
    "Salud" -> Icons.Filled.Favorite
    "Educación" -> Icons.Filled.School
    "Nómina" -> Icons.Filled.AttachMoney
    "Inversiones" -> Icons.AutoMirrored.Filled.TrendingUp
    "Freelance" -> Icons.Filled.Work
    "Alquiler" -> Icons.Filled.House
    "Regalos" -> Icons.Filled.CardGiftcard
    "Reembolsos" -> Icons.Filled.Replay
    "Ahorros" -> Icons.Filled.Savings
    else -> Icons.Filled.MoreHoriz
}

private fun filterTransactions(transacciones: List<Transaccion>, filter: String): List<Transaccion> {
    return when (filter.uppercase()) {
        "INGRESO" -> transacciones.filter { it.tipo.equals("INGRESO", true) }
        "GASTO" -> transacciones.filter { it.tipo.equals("GASTO", true) }
        else -> transacciones
    }
}
