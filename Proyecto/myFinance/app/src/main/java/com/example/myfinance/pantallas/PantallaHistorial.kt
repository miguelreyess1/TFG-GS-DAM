package com.example.myfinance.pantallas

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.myfinance.componentes.BarraNavegacion
import com.example.myfinance.componentes.Header
import com.example.myfinance.data.local.AppDatabase
import com.example.myfinance.data.repository.TransaccionRepository
import com.example.myfinance.viewmodel.TransaccionViewModel
import com.example.myfinance.viewmodel.TransaccionViewModelFactory
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun PantallaHistorial(navController: NavController) {
    val context = LocalContext.current
    val appDatabase = AppDatabase.getDatabase(context)
    val transaccionRepository = TransaccionRepository(appDatabase.transaccionDao())
    val transaccionViewModel: TransaccionViewModel = viewModel(
        factory = TransaccionViewModelFactory(transaccionRepository)
    )

    val transacciones by transaccionViewModel.transacciones.collectAsState()

    Scaffold(
        topBar = { Header() },
        bottomBar = { BarraNavegacion(navController) }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .background(Color.LightGray)
                .padding(16.dp)
        ) {
            Text(
                text = "Historial de Transacciones",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(transacciones) { transaccion ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(16.dp)
                        ) {
                            Text(
                                text = transaccion.descripcion,
                                style = MaterialTheme.typography.bodyLarge
                            )
                            Text(
                                text = "${transaccion.tipo} - ${transaccion.monto} €",
                                style = MaterialTheme.typography.bodyMedium
                            )
                            Text(
                                text = formatearFecha(transaccion.fecha),
                                style = MaterialTheme.typography.labelSmall,
                                color = Color.Gray
                            )
                        }
                    }
                }
            }
        }
    }
}

fun formatearFecha(date: java.util.Date): String {
    val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    return formatter.format(date)
}
