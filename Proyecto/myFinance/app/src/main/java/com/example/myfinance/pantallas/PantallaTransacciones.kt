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

/**
 * Pantalla para crear nuevas transacciones, con selector de tipo (gasto/ingreso),
 * descripción, selección de categoría y campo de cantidad.
 *
 * Inserta una transacción en la base de datos al pulsar el botón y
 * repuebla categorías si la tabla está vacía.
 *
 * @param navController Controlador de navegación para la barra inferior.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaTransacciones(navController: NavController) {
    // Inicialización de repositorios y scope para coroutines
    val context = LocalContext.current
    val db = AppDatabase.getDatabase(context)
    val catRepo = CategoriaRepository(db.categoriaDao())
    val transRepo = TransaccionRepository(db.transaccionDao())
    val scope = rememberCoroutineScope()

    // Estado local para inputs y datos cargados
    var tipo by remember { mutableStateOf("gasto") }                      // "gasto" o "ingreso"
    var cantidadText by remember { mutableStateOf("") }                   // Texto cantidad
    var descripcionText by remember { mutableStateOf("") }                // Texto descripción
    var categorias by remember { mutableStateOf<List<Categoria>>(emptyList()) } // Lista de categorías
    var seleccionada by remember { mutableStateOf<Categoria?>(null) }     // Categoría elegida

    // Carga inicial de categorías y si no hay ninguna, inserta ejemplos
    LaunchedEffect(Unit) {
        categorias = catRepo.getAll()
        if (categorias.isEmpty()) {
            val ejemplos = listOf(
                Categoria(nombre = "Alimentación", tipo = "gasto"),
                // ... otros ejemplos ...
                Categoria(nombre = "Otros", tipo = "ambos")
            )
            ejemplos.forEach { catRepo.insert(it) }
            categorias = catRepo.getAll()
        }
    }

    // Colores del tema
    val primary = MaterialTheme.colorScheme.primary
    val onPrimary = MaterialTheme.colorScheme.onPrimary
    val background = MaterialTheme.colorScheme.background
    val surface = MaterialTheme.colorScheme.surface
    val onBackground = MaterialTheme.colorScheme.onBackground
    val surfaceVariant = MaterialTheme.colorScheme.surfaceVariant
    val onSurfaceVariant = MaterialTheme.colorScheme.onSurfaceVariant

    Scaffold(
        topBar = { Header() },                            // Cabecera con logo y título
        bottomBar = { BarraNavegacion(navController) },   // Navegación inferior
        containerColor = background
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(background)
                .padding(padding)                       // Ajuste por Scaffold
                .padding(16.dp),                        // Padding interno
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Selector de tipo de transacción (Gasto/Ingreso)
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                listOf("gasto" to "Gasto", "ingreso" to "Ingreso").forEach { (valStr, lbl) ->
                    val sel = tipo == valStr
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp)
                            .background(
                                color = if (sel) primary else surface,
                                shape = RoundedCornerShape(8.dp)
                            )
                            .clickable {
                                tipo = valStr            // Cambia tipo y reinicia selección
                                seleccionada = null
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = lbl,
                            color = if (sel) onPrimary else onBackground,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            }

            // Campo de texto para descripción de la transacción
            OutlinedTextField(
                value = descripcionText,
                onValueChange = { descripcionText = it },
                label = { Text("Descripción") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = primary,
                    unfocusedBorderColor = onSurfaceVariant,
                    cursorColor = primary,
                    containerColor = surface,
                    focusedLabelColor = onBackground,
                    unfocusedLabelColor = onSurfaceVariant
                )
            )

            // Selector de categoría en grid de 4 columnas
            Text(
                text = "Categoría",
                style = MaterialTheme.typography.bodyLarge,
                color = onBackground
            )
            LazyVerticalGrid(
                columns = GridCells.Fixed(4),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.height(240.dp)
            ) {
                items(categorias.filter { it.tipo == "ambos" || it.tipo == tipo }) { cat ->
                    val sel = cat == seleccionada
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.clickable { seleccionada = cat } // Selecciona categoría
                    ) {
                        Box(
                            modifier = Modifier
                                .size(56.dp)
                                .background(
                                    color = if (sel) primary else surfaceVariant,
                                    shape = CircleShape
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = iconoParaCategoria(cat.nombre),
                                contentDescription = cat.nombre,
                                modifier = Modifier.size(28.dp),
                                tint = if (sel) onPrimary else primary
                            )
                        }
                        Spacer(Modifier.height(4.dp))
                        Text(
                            text = cat.nombre,
                            style = MaterialTheme.typography.bodySmall,
                            color = onBackground,
                            maxLines = 1
                        )
                    }
                }
            }

            // Campo de texto para la cantidad, solo números y punto
            OutlinedTextField(
                value = cantidadText,
                onValueChange = { new ->
                    if (new.matches(Regex("^\\d*\\.?\\d*$")))
                        cantidadText = new
                },
                label = { Text("Cantidad") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = primary,
                    unfocusedBorderColor = onSurfaceVariant,
                    cursorColor = primary,
                    containerColor = surface,
                    focusedLabelColor = onBackground,
                    unfocusedLabelColor = onSurfaceVariant
                ),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            // Botón para añadir la transacción
            Button(
                onClick = {
                    // Inserta en la BD solo si hay descripción, cantidad válida y categoría
                    val monto = cantidadText.toDoubleOrNull() ?: return@Button
                    val catId = seleccionada?.id ?: return@Button
                    scope.launch {
                        transRepo.insert(
                            Transaccion(
                                tipo        = tipo.uppercase(Locale.ROOT),
                                monto       = monto,
                                descripcion = descripcionText,
                                fecha       = Date(),
                                categoriaId = catId
                            )
                        )
                        // Resetea campos tras guardar
                        cantidadText = ""
                        descripcionText = ""
                        seleccionada = null
                    }
                },
                enabled = seleccionada != null
                        && cantidadText.toDoubleOrNull() != null
                        && descripcionText.isNotEmpty(),
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = primary,
                    contentColor = onPrimary
                )
            ) {
                Text(text = "Añadir transacción")
            }
        }
    }
}

/**
 * Devuelve un ícono asociado al nombre de categoría proporcionado.
 *
 * Mapea nombres de categorías comunes a los ImageVector de Material Icons.
 * Si el nombre no coincide con ninguno de los casos definidos, retorna un ícono genérico.
 *
 * @param nombre Nombre de la categoría (por ejemplo, "Alimentación", "Transporte").
 * @return ImageVector correspondiente al ícono de la categoría.
 */
private fun iconoParaCategoria(nombre: String): ImageVector =
    when (nombre) {
        "Alimentación"  -> Icons.Filled.Fastfood       // Ícono para gastos de comida
        "Transporte"    -> Icons.Filled.DirectionsCar  // Ícono para transporte
        "Hogar"         -> Icons.Filled.Home           // Ícono para gastos del hogar
        "Compras"       -> Icons.Filled.ShoppingBag    // Ícono para compras generales
        "Ocio"          -> Icons.Filled.Movie          // Ícono para entretenimiento
        "Salud"         -> Icons.Filled.Favorite       // Ícono para salud y bienestar
        "Educación"     -> Icons.Filled.School         // Ícono para educación
        "Nómina"        -> Icons.Filled.AttachMoney    // Ícono para ingresos por nómina
        "Inversiones"   -> Icons.AutoMirrored.Filled.TrendingUp // Ícono para inversiones
        "Freelance"     -> Icons.Filled.Work           // Ícono para trabajos freelance
        "Alquiler"      -> Icons.Filled.House          // Ícono para pago de alquiler
        "Regalos"       -> Icons.Filled.CardGiftcard   // Ícono para regalos
        "Reembolsos"    -> Icons.Filled.Replay         // Ícono para reembolsos
        "Ahorros"       -> Icons.Filled.Savings        // Ícono para ahorros
        else            -> Icons.Filled.MoreHoriz      // Ícono genérico para otras categorías
    }