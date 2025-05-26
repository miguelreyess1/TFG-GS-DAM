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

/**
 * Pantalla que muestra el historial de transacciones con opción de filtrar
 * por tipo y navegación inferior.
 *
 * @param navController Controlador de navegación para cambiar de pantalla.
 */
@Composable
fun PantallaHistorial(navController: NavController) {
    // Contexto para acceso a la base de datos
    val context = LocalContext.current
    val appDatabase = AppDatabase.getDatabase(context)

    // Repositorios para transacciones y categorías
    val transaccionRepository = TransaccionRepository(appDatabase.transaccionDao())
    val categoriaRepository = CategoriaRepository(appDatabase.categoriaDao())

    // ViewModel que expone un Flow de transacciones
    val transaccionViewModel: TransaccionViewModel = viewModel(
        factory = TransaccionViewModelFactory(transaccionRepository)
    )

    // Estado observado de transacciones y lista de categorías
    val transacciones by transaccionViewModel.transacciones.collectAsState(initial = emptyList())
    val categorias by produceState(initialValue = emptyList<Categoria>()) {
        value = categoriaRepository.getAll()
    }

    // Filtro seleccionado: TODOS, INGRESO o GASTO
    var selectedFilter by remember { mutableStateOf("TODOS") }

    Scaffold(
        topBar = { Header() },                                  // Cabecera común
        bottomBar = { BarraNavegacion(navController) },        // Barra de navegación inferior
        containerColor = MaterialTheme.colorScheme.background
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)                              // Respeta las barras del Scaffold
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            // Sección de título y chips de filtro
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Historial de Transacciones",
                    style = MaterialTheme.typography.titleLarge.copy(
                        color = MaterialTheme.colorScheme.onBackground
                    ),
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    // Creación de chips para cada filtro posible
                    listOf("TODOS", "INGRESO", "GASTO").forEach { filter ->
                        FilterChip(
                            selected = selectedFilter == filter,
                            onClick = { selectedFilter = filter },
                            label = { Text(filter) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = MaterialTheme.colorScheme.primary,
                                selectedLabelColor     = MaterialTheme.colorScheme.onPrimary
                            )
                        )
                    }
                }
            }

            // Lista de transacciones filtradas
            TransactionList(
                transacciones = filterTransactions(transacciones, selectedFilter),
                categorias     = categorias
            )
        }
    }
}

/**
 * Composable que recibe una lista de transacciones y las agrupa por fecha,
 * mostrando cada día como encabezado y sus transacciones en un LazyColumn.
 *
 * @param transacciones Lista de [Transaccion] a mostrar.
 * @param categorias Lista de [Categoria] para resolución de nombres.
 */
@Composable
private fun TransactionList(transacciones: List<Transaccion>, categorias: List<Categoria>) {
    // Agrupa por fecha formateada en dd MMM yyyy (ej. "26 May 2025")
    val groupedTransactions = transacciones.groupBy {
        SimpleDateFormat("dd MMM yyyy", Locale("es", "ES")).format(it.fecha)
    }

    LazyColumn(
        modifier = Modifier.padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        groupedTransactions.forEach { (fecha, listaDia) ->
            // Encabezado de fecha
            item {
                Text(
                    text = fecha,
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }
            // Ítems de transacción del día
            items(listaDia) { transaccion ->
                TransactionItem(
                    transaccion = transaccion,
                    categoria    = categorias.find { it.id == transaccion.categoriaId }
                )
            }
        }
    }
}

/**
 * Composable que muestra los detalles de una única transacción dentro de una Card.
 *
 * @param transaccion Objeto [Transaccion] a renderizar.
 * @param categoria   [Categoria] asociada a la transacción (puede ser null).
 */
@Composable
private fun TransactionItem(transaccion: Transaccion, categoria: Categoria?) {
    // Color según tipo: ingresos en primary, gastos en error
    val tipoColor = if (transaccion.tipo.equals("INGRESO", ignoreCase = true))
        MaterialTheme.colorScheme.primary
    else
        MaterialTheme.colorScheme.error

    // Icono basado en la categoría (por nombre)
    val icono = remember(transaccion.categoriaId) {
        iconoParaCategoria(categoria?.nombre.orEmpty())
    }

    // Formato de hora para mostrar sólo HH:mm
    val formatoHora = remember { SimpleDateFormat("HH:mm", Locale.getDefault()) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Icono de categoría
            Icon(
                imageVector       = icono,
                contentDescription = null,
                modifier          = Modifier.size(32.dp),
                tint              = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.width(12.dp))
            // Columna con descripción y hora
            Column(Modifier.weight(1f)) {
                Text(
                    text = transaccion.descripcion.ifEmpty { categoria?.nombre.orEmpty() },
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Medium),
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    text  = formatoHora.format(transaccion.fecha),
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            // Columna con monto y tipo
            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = "${if (transaccion.tipo.equals("GASTO", true)) "-" else ""}${"%.2f€".format(transaccion.monto)}",
                    style = MaterialTheme.typography.titleMedium.copy(
                        color      = tipoColor,
                        fontWeight = FontWeight.Bold
                    )
                )
                Text(
                    text = transaccion.tipo.replaceFirstChar { it.uppercase() },
                    style = MaterialTheme.typography.labelSmall.copy(color = tipoColor)
                )
            }
        }
    }
}

/**
 * Devuelve un [ImageVector] según el nombre de categoría recibido.
 *
 * Mapea nombres comunes a iconos de Material.
 *
 * @param nombre Nombre de la categoría.
 * @return Icono asociado o `Icons.Filled.MoreHoriz` por defecto.
 */
private fun iconoParaCategoria(nombre: String): ImageVector = when (nombre) {
    "Alimentación"  -> Icons.Filled.Fastfood
    "Transporte"    -> Icons.Filled.DirectionsCar
    "Hogar"         -> Icons.Filled.Home
    "Compras"       -> Icons.Filled.ShoppingBag
    "Ocio"          -> Icons.Filled.Movie
    "Salud"         -> Icons.Filled.Favorite
    "Educación"     -> Icons.Filled.School
    "Nómina"        -> Icons.Filled.AttachMoney
    "Inversiones"   -> Icons.AutoMirrored.Filled.TrendingUp
    "Freelance"     -> Icons.Filled.Work
    "Alquiler"      -> Icons.Filled.House
    "Regalos"       -> Icons.Filled.CardGiftcard
    "Reembolsos"    -> Icons.Filled.Replay
    "Ahorros"       -> Icons.Filled.Savings
    else            -> Icons.Filled.MoreHoriz
}

/**
 * Filtra la lista de transacciones según el tipo seleccionado.
 *
 * @param transacciones Lista original de [Transaccion].
 * @param filter Cadena que indica el filtro ("INGRESO", "GASTO" o cualquier otra para todos).
 * @return Lista filtrada de transacciones.
 */
private fun filterTransactions(transacciones: List<Transaccion>, filter: String): List<Transaccion> {
    return when (filter.uppercase()) {
        "INGRESO" -> transacciones.filter { it.tipo.equals("INGRESO", true) }
        "GASTO"   -> transacciones.filter { it.tipo.equals("GASTO", true) }
        else      -> transacciones
    }
}
