package com.example.myfinance.componentes

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myfinance.R

/**
 * Cabecera de la pantalla que muestra el logo y titulo de la app.
 *
 * Este Composable se extiende al ancho disponible, con un fondo
 * definido por el tema y una elevación para crear sombra.
 *
 * @param titulo Texto que se muestra junto al logo. Por defecto "MyFinance".
 */
@Composable
fun Header(titulo: String = "MyFinance") {
    // Contenedor de Material con color de superficie y sombra
    Surface(
        color = MaterialTheme.colorScheme.surface,
        shadowElevation = 4.dp,
        modifier = Modifier.fillMaxWidth()
    ) {
        // Fila horizontal alineada al centro verticalmente
        Row(
            modifier = Modifier
                .fillMaxWidth()                          // Ocupa el ancho completo
                .padding(horizontal = 24.dp, vertical = 30.dp), // Espaciado interno
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start     // Elementos empezando por la izquierda
        ) {
            // Logo de la aplicación
            Image(
                painter = painterResource(id = R.drawable.app_logo),
                contentDescription = "Logo de la aplicación",
                modifier = Modifier.size(40.dp),         // Tamaño fijo del ícono
                contentScale = ContentScale.Fit          // Ajuste de la imagen dentro del espacio
            )
            Spacer(modifier = Modifier.width(16.dp))      // Separador horizontal entre logo y texto
            // Título de la pantalla
            Text(
                text = titulo,
                style = MaterialTheme.typography.titleLarge, // Estilo de texto del tema
                color = MaterialTheme.colorScheme.onSurface, // Color de texto sobre la superficie
                fontSize = 20.sp                              // Tamaño de fuente explícito
            )
        }
    }
}
