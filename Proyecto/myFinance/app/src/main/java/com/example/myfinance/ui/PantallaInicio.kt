package com.example.myfinance.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myfinance.viewmodel.FinanceViewModel

@Composable
fun PantallaInicio(viewModel: FinanceViewModel) {
    val saldo by viewModel.saldoHucha.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Dinero Total en la Cuenta:",
            style = MaterialTheme.typography.headlineMedium
        )
        Text(
            text = "$$saldo",
            style = MaterialTheme.typography.displaySmall
        )
        Spacer(modifier = Modifier.height(32.dp))
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            elevation = CardDefaults.cardElevation(8.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Hucha Principal",
                    style = MaterialTheme.typography.headlineSmall
                )
                Text(
                    text = "Saldo: $$saldo",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}
