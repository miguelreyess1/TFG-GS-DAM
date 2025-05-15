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

@Composable
fun PantallaPerfil(
    navController: NavController,
    isDarkTheme: Boolean,
    onToggleTheme: (Boolean) -> Unit
) {
    // ViewModel de usuario
    val context = LocalContext.current.applicationContext as Application
    val usuarioViewModel: UsuarioViewModel = viewModel(
        factory = UsuarioViewModelFactory(context)
    )
    // Estado del perfil
    val profileState by usuarioViewModel.profileState.collectAsState()

    // Estados de UI
    var isEditing by rememberSaveable { mutableStateOf(false) }
    var imageUri by rememberSaveable { mutableStateOf<Uri?>(null) }
    var editingName by rememberSaveable { mutableStateOf(profileState?.nombre.orEmpty()) }

    LaunchedEffect(profileState?.nombre) {
        if (!isEditing) {
            editingName = profileState?.nombre.orEmpty()
        }
    }

    // Selector de imágenes
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? -> imageUri = uri }

    Scaffold(
        topBar = { Header() },
        bottomBar = { BarraNavegacion(navController) },
        containerColor = MaterialTheme.colorScheme.background
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            // Botones de tema
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(onClick = { onToggleTheme(false) }) {
                    Text("Claro")
                }
                Button(onClick = { onToggleTheme(true) }) {
                    Text("Oscuro")
                }
            }

            ProfileImage(
                imageUri = imageUri,
                isEditing = isEditing,
                onImageSelect = { launcher.launch("image/*") }
            )

            if (isEditing) {
                EditProfileSection(
                    userName = editingName,
                    onNameChange = { editingName = it },
                    onSave = {
                        usuarioViewModel.saveProfile(
                            editingName,
                            imageUri?.toString()
                        )
                        isEditing = false
                    }
                )
            } else {
                ViewProfileSection(
                    userName = profileState?.nombre.orEmpty(),
                    onEdit = { isEditing = true }
                )
            }
        }
    }
}

@Composable
private fun ProfileImage(
    imageUri: Uri?,
    isEditing: Boolean,
    onImageSelect: () -> Unit
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(128.dp)
            .clip(CircleShape)
            .border(2.dp, MaterialTheme.colorScheme.primary, CircleShape)
            .clickable(enabled = isEditing) { onImageSelect() }
    ) {
        if (imageUri != null) {
            Image(
                painter = rememberAsyncImagePainter(imageUri),
                contentDescription = "Foto de perfil",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        } else {
            Icon(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = "Avatar",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.fillMaxSize()
            )
        }

        if (isEditing) {
            Icon(
                imageVector = Icons.Default.CameraAlt,
                contentDescription = "Cambiar foto",
                tint = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .background(MaterialTheme.colorScheme.primary, CircleShape)
                    .padding(6.dp)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun EditProfileSection(
    userName: String,
    onNameChange: (String) -> Unit,
    onSave: () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = userName,
            onValueChange = onNameChange,
            label = { Text("Tu nombre") },
            leadingIcon = { Icon(Icons.Default.Edit, contentDescription = "Nombre") },
            singleLine = true,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                cursorColor = MaterialTheme.colorScheme.primary
            )
        )

        Button(
            onClick = onSave,
            enabled = userName.isNotBlank(),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            )
        ) {
            Text("Guardar cambios")
        }
    }
}

@Composable
private fun ViewProfileSection(
    userName: String,
    onEdit: () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = userName,
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onBackground
        )

        FilledTonalButton(
            onClick = onEdit,
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            )
        ) {
            Icon(Icons.Default.Edit, contentDescription = "Editar")
            Spacer(Modifier.width(8.dp))
            Text("Editar perfil")
        }
    }
}
