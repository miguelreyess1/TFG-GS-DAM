package com.example.myfinance.pantallas

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myfinance.componentes.BarraNavegacion
import com.example.myfinance.componentes.Header
import com.example.myfinance.data.local.AppDatabase
import com.example.myfinance.data.model.Transaccion
import com.example.myfinance.data.repository.TransaccionRepository
import kotlinx.coroutines.launch
import java.util.*

@Composable
fun PantallaTransacciones(navController: NavController) {
    val db = AppDatabase.getDatabase(LocalContext.current)
    val repo = TransaccionRepository(db.transaccionDao())
    val scope = rememberCoroutineScope()
    var tipo by remember { mutableStateOf("gasto") }
    var cantidadText by remember { mutableStateOf("") }

    Scaffold(
        topBar = { Header() },
        bottomBar = { BarraNavegacion(navController) },
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.LightGray)
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                listOf("gasto" to "Gasto", "ingreso" to "Ingreso").forEach { (value, label) ->
                    val selected = tipo == value
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp)
                            .background(
                                color = if (selected) MaterialTheme.colorScheme.primary else Color.White,
                                shape = RoundedCornerShape(8.dp)
                            )
                            .clickable { tipo = value },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = label,
                            color = if (selected) Color.White else Color.Black,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            }

            OutlinedTextField(
                value = cantidadText,
                onValueChange = { cantidadText = it },
                label = { Text("Cantidad") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(16.dp))
            Button(
                onClick = {
                    val monto = cantidadText.toDoubleOrNull() ?: 0.0
                    if (monto > 0) {
                        scope.launch {
                            repo.insert(
                                Transaccion(
                                    tipo = tipo,
                                    monto = monto,
                                    descripcion = "",
                                    fecha = Date(),
                                    categoriaId = 0
                                )
                            )
                            cantidadText = ""
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Añadir")
            }
        }
    }
}
