package com.example.myfinance.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Data classes permanecen iguales
data class Categoria(val id: Int, val nombre: String)
data class Transaccion(val id: Int, val tipo: String, val importe: Double, val categoria: Categoria)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaTransacciones() {
    // Lista de categorías predefinidas (sin opción de añadir nuevas)
    val categorias = remember {
        listOf(
            Categoria(1, "Comida"),
            Categoria(2, "Transporte"),
            Categoria(3, "Nómina"),
            Categoria(4, "Ocio"),
            Categoria(5, "Salud")
        )
    }

    val transacciones = remember { mutableStateListOf<Transaccion>() }
    var selectedTransactionType by remember { mutableStateOf("Ingreso") }
    var importeInput by remember { mutableStateOf("") }
    var selectedCategoria by remember { mutableStateOf<Categoria?>(null) }
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        // Header
        Text(
            text = "Gestión de Transacciones",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        // Sección de tipo de transacción
        Text(
            text = "Tipo de operación:",
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.height(8.dp))
        SegmentedControl(
            options = listOf("Ingreso", "Gasto"),
            selectedOption = selectedTransactionType,
            onOptionSelected = { selectedTransactionType = it }
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Selector de categoría usando ExposedDropdownMenuBox
        Text(
            text = "Categoría:",
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.height(8.dp))
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            OutlinedTextField(
                value = selectedCategoria?.nombre ?: "Selecciona categoría",
                onValueChange = { },
                readOnly = true,
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.surfaceVariant,
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                ),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor() // Habilita el comportamiento de menú desplegable
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.fillMaxWidth(0.95f)
            ) {
                categorias.forEach { categoria ->
                    DropdownMenuItem(
                        text = { Text(categoria.nombre) },
                        onClick = {
                            selectedCategoria = categoria
                            expanded = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Campo de importe
        OutlinedTextField(
            value = importeInput,
            onValueChange = { if (it.matches(Regex("^\\d*\\.?\\d*"))) importeInput = it },
            label = { Text("Importe") },
            leadingIcon = { Icon(Icons.Default.AttachMoney, contentDescription = "Importe") },
            placeholder = { Text("0.00") },
            suffix = { Text("€") },
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Botón de añadir
        Button(
            onClick = {
                val importe = importeInput.toDoubleOrNull() ?: 0.0
                if (importe > 0 && selectedCategoria != null) {
                    transacciones.add(
                        Transaccion(
                            id = transacciones.size + 1,
                            tipo = selectedTransactionType,
                            importe = importe,
                            categoria = selectedCategoria!!
                        )
                    )
                    importeInput = ""
                    selectedCategoria = null
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Añadir transacción", fontSize = 16.sp)
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Lista de transacciones
        Text(
            text = "Historial (${transacciones.size})",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        if (transacciones.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No hay transacciones registradas",
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        } else {
            transacciones.reversed().forEach { transaccion ->
                TransactionCard(
                    tipo = transaccion.tipo,
                    categoria = transaccion.categoria.nombre,
                    importe = transaccion.importe,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }
        }
    }
}

// Componente personalizado para tarjetas de transacción
@Composable
private fun TransactionCard(tipo: String, categoria: String, importe: Double, modifier: Modifier = Modifier) {
    val color = if (tipo == "Ingreso") Color(0xFF4CAF50) else Color(0xFFF44336)
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = categoria,
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.SemiBold)
                )
                Text(
                    text = tipo,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Text(
                text = "${if (tipo == "Ingreso") "+" else "-"} ${"%.2f".format(importe)}€",
                color = color,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

// Control segmentado para tipo de transacción
@Composable
private fun SegmentedControl(
    options: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.surfaceVariant,
                shape = RoundedCornerShape(12.dp)
            )
            .padding(4.dp)
    ) {
        options.forEach { option ->
            val isSelected = option == selectedOption
            Box(
                modifier = Modifier
                    .weight(1f)
                    .clickable { onOptionSelected(option) }
                    .background(
                        color = if (isSelected) MaterialTheme.colorScheme.primaryContainer else Color.Transparent,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(vertical = 12.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = option,
                    color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant,
                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                )
            }
        }
    }
}
