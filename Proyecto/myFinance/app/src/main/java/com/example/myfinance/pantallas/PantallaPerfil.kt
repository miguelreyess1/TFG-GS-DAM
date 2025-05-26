package com.example.myfinance.pantallas

import android.app.Application
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.myfinance.componentes.BarraNavegacion
import com.example.myfinance.componentes.Header
import com.example.myfinance.viewmodel.UsuarioViewModel
import com.example.myfinance.viewmodel.UsuarioViewModelFactory
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.filled.NightsStay
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.ui.graphics.Color

/**
 * Pantalla de perfil de usuario que permite ver y editar nombre e imagen,
 * además de cambiar el tema de la aplicación.
 *
 * @param navController Controlador de navegación para la barra inferior.
 * @param onToggleTheme Callback para alternar entre tema claro (false) y oscuro (true).
 */
@Composable
fun PantallaPerfil(
    navController: NavController,
    onToggleTheme: (Boolean) -> Unit
) {
    // Contexto de la aplicación para ViewModel
    val context = LocalContext.current.applicationContext as Application

    // ViewModel que maneja el estado del usuario
    val usuarioViewModel: UsuarioViewModel = viewModel(
        factory = UsuarioViewModelFactory(context)
    )
    // Estado de perfil que expone nombre e imagen guardados
    val profileState by usuarioViewModel.profileState.collectAsState()

    // Modos de UI: edición o visualización
    var isEditing by rememberSaveable { mutableStateOf(false) }
    var imageUri by rememberSaveable { mutableStateOf<Uri?>(null) }
    // Nombre en edición, inicializado con el nombre actual
    var editingName by rememberSaveable { mutableStateOf(profileState?.nombre.orEmpty()) }

    // Cuando cambia profileState.nombre y no estamos editando, actualizamos editingName
    LaunchedEffect(profileState?.nombre) {
        if (!isEditing) {
            editingName = profileState?.nombre.orEmpty()
        }
    }

    // Launcher para seleccionar imagen de galería
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUri = uri  // Actualiza URI al seleccionar imagen
    }

    Scaffold(
        topBar = { Header() },                            // Cabecera con logo y título
        bottomBar = { BarraNavegacion(navController) },  // Navegación inferior
        containerColor = MaterialTheme.colorScheme.background
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)                        // Ajusta por Scaffold
                .fillMaxSize()
                .padding(24.dp),                        // Espaciado interno
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            // Composable que muestra/avatar de perfil y botón de selección
            ProfileImage(
                imageUri     = imageUri,
                isEditing    = isEditing,
                onImageSelect = { launcher.launch("image/*") }
            )

            if (isEditing) {
                // Sección de edición: input para nombre y botón guardar
                EditProfileSection(
                    userName     = editingName,
                    onNameChange = { editingName = it },
                    onSave       = {
                        // Guarda cambios en ViewModel y sale de modo edición
                        usuarioViewModel.saveProfile(
                            editingName,
                            imageUri?.toString()
                        )
                        isEditing = false
                    }
                )
            } else {
                // Sección de visualización: muestra nombre y botón editar
                ViewProfileSection(
                    userName = profileState?.nombre.orEmpty(),
                    onEdit   = { isEditing = true }
                )
            }

            // Botones para alternar tema claro/oscuro
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterHorizontally)
            ) {
                // Botón Tema Claro
                Button(
                    onClick = { onToggleTheme(false) },
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp)
                        .clip(RoundedCornerShape(24.dp))
                        .border(
                            width = 2.dp,
                            color = MaterialTheme.colorScheme.primary,
                            shape = RoundedCornerShape(24.dp)
                        ),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White,
                        contentColor   = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Icon(
                        imageVector = Icons.Filled.WbSunny,
                        contentDescription = "Tema Claro",
                        modifier = Modifier.size(24.dp)
                    )
                }

                // Botón Tema Oscuro
                Button(
                    onClick = { onToggleTheme(true) },
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp)
                        .clip(RoundedCornerShape(24.dp))
                        .border(
                            width = 2.dp,
                            color = MaterialTheme.colorScheme.primary,
                            shape = RoundedCornerShape(24.dp)
                        ),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF121212),
                        contentColor   = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Icon(
                        imageVector = Icons.Filled.NightsStay,
                        contentDescription = "Tema Oscuro",
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }
    }
}

/**
 * Composable que muestra la imagen de perfil del usuario en un contenedor circular.
 * Permite seleccionar una nueva imagen si está en modo edición.
 *
 * @param imageUri     URI de la imagen de perfil; si es null, muestra un icono por defecto.
 * @param isEditing    Indica si se permite cambiar la imagen pulsando sobre el avatar.
 * @param onImageSelect Callback que se invoca al pulsar el contenedor en modo edición.
 */
@Composable
private fun ProfileImage(
    imageUri: Uri?,
    isEditing: Boolean,
    onImageSelect: () -> Unit
) {
    // Caja cuadrada que actúa como botón circular cuando isEditing == true
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(128.dp)                                         // Tamaño fijo del avatar
            .clip(CircleShape)                                    // Recorta en forma de círculo
            .border(2.dp, MaterialTheme.colorScheme.primary, CircleShape)  // Borde del círculo
            .clickable(enabled = isEditing) { onImageSelect() }   // Solo clicable en edición
    ) {
        if (imageUri != null) {
            // Si hay URI, cargamos y mostramos la imagen recortada
            Image(
                painter = rememberAsyncImagePainter(imageUri),
                contentDescription = "Foto de perfil",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()               // Ocupa el contenedor entero
            )
        } else {
            // Si no hay imagen, mostramos un icono de usuario
            Icon(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = "Avatar",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.fillMaxSize()
            )
        }

        if (isEditing) {
            // Indicador de cámara en la esquina inferior derecha para invitar a cambiar foto
            Icon(
                imageVector = Icons.Default.CameraAlt,
                contentDescription = "Cambiar foto",
                tint = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier
                    .align(Alignment.BottomEnd)                  // Posicionado en esquina
                    .background(MaterialTheme.colorScheme.primary, CircleShape) // Fondo circular
                    .padding(6.dp)                                // Espacio alrededor del icono
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
/**
 * Sección de edición de perfil que permite al usuario cambiar su nombre
 * y guardar los cambios.
 *
 * @param userName     Texto actual del nombre en el campo de edición.
 * @param onNameChange Callback que se invoca al modificar el texto del nombre.
 * @param onSave       Callback que se invoca al pulsar el botón de guardar.
 */
@Composable
private fun EditProfileSection(
    userName: String,
    onNameChange: (String) -> Unit,
    onSave: () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),      // Espacio entre elementos
        horizontalAlignment = Alignment.CenterHorizontally       // Centra horizontalmente
    ) {
        // Campo de texto para editar el nombre de usuario
        OutlinedTextField(
            value = userName,                                  // Valor mostrado
            onValueChange = onNameChange,                      // Actualiza estado externo
            label = { Text("Tu nombre") },                     // Etiqueta flotante
            leadingIcon = {                                   // Icono a la izquierda
                Icon(Icons.Default.Edit, contentDescription = "Nombre")
            },
            singleLine = true,                                 // Una sola línea de entrada
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = MaterialTheme.colorScheme.primary,  // Borde al enfocar
                cursorColor = MaterialTheme.colorScheme.primary          // Color del cursor
            )
        )

        // Botón para guardar los cambios, habilitado solo si el nombre no está vacío
        Button(
            onClick = onSave,
            enabled = userName.isNotBlank(),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,      // Fondo del botón
                contentColor = MaterialTheme.colorScheme.onPrimary       // Color del texto
            )
        ) {
            Text("Guardar cambios")                                 // Texto del botón
        }
    }
}

/**
 * Sección de visualización del perfil que muestra el nombre del usuario
 * y un botón para pasar al modo edición.
 *
 * @param userName Texto con el nombre actual del usuario.
 * @param onEdit   Callback que se invoca al pulsar el botón de editar.
 */
@Composable
private fun ViewProfileSection(
    userName: String,
    onEdit: () -> Unit
) {
    Column(
        // Separación uniforme entre elementos y centrado horizontal
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Muestra el nombre del usuario con estilo destacado
        Text(
            text = userName,
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onBackground
        )

        // Botón tonal para iniciar la edición del perfil
        FilledTonalButton(
            onClick = onEdit,
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,    // Fondo del botón
                contentColor = MaterialTheme.colorScheme.onPrimary     // Color del texto e ícono
            )
        ) {
            Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = "Editar perfil"
            )
            Spacer(Modifier.width(8.dp))  // Espacio entre ícono y texto
            Text("Editar perfil")
        }
    }
}