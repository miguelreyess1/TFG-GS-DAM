package com.example.myfinance.pantallas

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.myfinance.componentes.BarraNavegacion
import com.example.myfinance.componentes.Header
import com.example.myfinance.data.local.AppDatabase
import com.example.myfinance.data.model.Transaccion
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
    val transaccionViewModel: TransaccionViewModel = viewModel(
        factory = TransaccionViewModelFactory(transaccionRepository)
    )

    val transacciones by transaccionViewModel.transacciones.collectAsState()
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
                            label = { Text(filter.replaceFirstChar { it.uppercase() }) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = Color(0xFF6ADDB0),
                                selectedLabelColor = Color.White
                            )
                        )
                    }
                }
            }

            // Lista de transacciones
            LazyColumn(
                modifier = Modifier.padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(filterTransactions(transacciones, selectedFilter)) { transaccion ->
                    TransactionItem(transaccion = transaccion)
                }
            }
        }
    }
}

@Composable
private fun TransactionItem(transaccion: Transaccion) {
    val tipoColor = when (transaccion.tipo) {
        "INGRESO" -> Color(0xFF4CAF50)
        else -> Color(0xFFE53935)
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(Modifier.weight(1f)) {
                Text(
                    text = transaccion.descripcion,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Medium
                    )
                )
                Text(
                    text = formatearFecha(transaccion.fecha),
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.Gray
                )
            }

            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = "${transaccion.monto} €".replace(".", ","),
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = tipoColor,
                        fontWeight = FontWeight.Bold
                    )
                )
                Text(
                    text = transaccion.tipo.lowercase().capitalize(Locale.ROOT),
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = tipoColor
                    )
                )
            }
        }
    }
}

private fun filterTransactions(transacciones: List<Transaccion>, filter: String): List<Transaccion> {
    return when (filter) {
        "INGRESO" -> transacciones.filter { it.tipo == "INGRESO" }
        "GASTO" -> transacciones.filter { it.tipo == "GASTO" }
        else -> transacciones
    }
}

private fun formatearFecha(date: Date): String {
    val formatter = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
    return formatter.format(date)
}