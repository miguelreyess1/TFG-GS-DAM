package com.example.myfinance.pantallas

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myfinance.componentes.BarraNavegacion
import com.example.myfinance.componentes.Header
import com.example.myfinance.data.local.AppDatabase
import com.example.myfinance.data.model.Categoria
import com.example.myfinance.data.model.Transaccion
import com.example.myfinance.data.repository.CategoriaRepository
import com.example.myfinance.data.repository.TransaccionRepository
import kotlinx.coroutines.launch
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaTransacciones(navController: NavController) {
    val context = LocalContext.current
    val db = AppDatabase.getDatabase(context)
    val catRepo = CategoriaRepository(db.categoriaDao())
    val transRepo = TransaccionRepository(db.transaccionDao())
    val scope = rememberCoroutineScope()

    var tipo by remember { mutableStateOf("gasto") }
    var cantidadText by remember { mutableStateOf("") }
    var descripcionText by remember { mutableStateOf("") } // Nuevo estado para descripción
    var categorias by remember { mutableStateOf<List<Categoria>>(emptyList()) }
    var seleccionada by remember { mutableStateOf<Categoria?>(null) }
    val green = Color(0xFF62DBB1)

    LaunchedEffect(Unit) {
        categorias = catRepo.getAll()
        if (categorias.isEmpty()) {
            val ejemplos = listOf(
                Categoria(nombre = "Alimentación", tipo = "gasto"),
                Categoria(nombre = "Transporte", tipo = "gasto"),
                Categoria(nombre = "Hogar", tipo = "gasto"),
                Categoria(nombre = "Compras", tipo = "gasto"),
                Categoria(nombre = "Ocio", tipo = "gasto"),
                Categoria(nombre = "Salud", tipo = "gasto"),
                Categoria(nombre = "Educación", tipo = "gasto"),

                Categoria(nombre = "Nómina", tipo = "ingreso"),
                Categoria(nombre = "Inversiones", tipo = "ingreso"),
                Categoria(nombre = "Freelance", tipo = "ingreso"),
                Categoria(nombre = "Alquiler", tipo = "ingreso"),
                Categoria(nombre = "Regalos", tipo = "ingreso"),
                Categoria(nombre = "Reembolsos", tipo = "ingreso"),
                Categoria(nombre = "Ahorros", tipo = "ingreso"),

                Categoria(nombre = "Otros", tipo = "ambos")
            )
            ejemplos.forEach { catRepo.insert(it) }
            categorias = catRepo.getAll()
        }
    }

    fun iconoPara(cat: Categoria): ImageVector = when (cat.nombre) {
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

    val visibles = remember(categorias, tipo) {
        categorias.filter { cat ->
            cat.tipo == "ambos" || cat.tipo == tipo
        }
    }

    Scaffold(
        topBar = { Header() },
        bottomBar = { BarraNavegacion(navController) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFFAFAFA))
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
             Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                listOf("gasto" to "Gasto", "ingreso" to "Ingreso").forEach { (valStr, lbl) ->
                    val sel = tipo == valStr
                    Box(
                        Modifier
                            .weight(1f)
                            .height(48.dp)
                            .background(if (sel) green else Color.White, RoundedCornerShape(8.dp))
                            .clickable {
                                tipo = valStr
                                seleccionada = null
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            lbl,
                            color = if (sel) Color.White else Color.Black,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            }

            OutlinedTextField(
                value = descripcionText,
                onValueChange = { descripcionText = it },
                label = { Text("Descripción") },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = green,
                    unfocusedBorderColor = Color.Gray
                ),
                singleLine = true
            )

            Text("Categoría", style = MaterialTheme.typography.bodyLarge)
            LazyVerticalGrid(
                columns = GridCells.Fixed(4),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.height(240.dp)
            ) {
                items(visibles) { cat ->
                    val sel = cat == seleccionada
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.clickable { seleccionada = cat }
                    ) {
                        Box(
                            Modifier
                                .size(56.dp)
                                .background(if (sel) green else Color(0xFFF0F0F0), CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                iconoPara(cat),
                                contentDescription = cat.nombre,
                                modifier = Modifier.size(28.dp))
                        }
                        Spacer(Modifier.height(4.dp))
                        Text(cat.nombre, style = MaterialTheme.typography.bodySmall, maxLines = 1)
                    }
                }
            }

            OutlinedTextField(
                value = cantidadText,
                onValueChange = { new ->
                    if (new.matches(Regex("^\\d*\\.?\\d*$"))) {
                        cantidadText = new
                    }
                },
                label = { Text("Cantidad") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = green,
                    unfocusedBorderColor = Color.Gray
                ),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            Button(
                onClick = {
                    val monto = cantidadText.toDoubleOrNull() ?: return@Button
                    val catId = seleccionada?.id ?: return@Button
                    scope.launch {
                        transRepo.insert(
                            Transaccion(
                                tipo = tipo.uppercase(Locale.ROOT),
                                monto = monto,
                                descripcion = descripcionText,
                                fecha = Date(),
                                categoriaId = catId
                            )
                        )
                        cantidadText = ""
                        descripcionText = ""
                        seleccionada = null
                    }
                },
                enabled = seleccionada != null && cantidadText.toDoubleOrNull() != null && descripcionText.isNotEmpty(),
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = green)
            ) {
                Text("Añadir transacción", color = Color.White)
            }
        }
    }
}