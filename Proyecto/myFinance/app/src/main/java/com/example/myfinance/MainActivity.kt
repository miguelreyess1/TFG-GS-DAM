package com.example.myfinance

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.room.Room
import com.example.myfinance.data.AppDatabase
import com.example.myfinance.repository.FinanceRepository
import com.example.myfinance.ui.theme.MyFinanceTheme
import com.example.myfinance.viewmodel.FinanceViewModel
import com.example.myfinance.viewmodel.FinanceViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Crea la instancia de la base de datos usando Room
        val database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "myfinance_db"
        ).build()
        // Crea el repositorio y la fábrica del ViewModel
        val repository = FinanceRepository(database)
        val viewModelFactory = FinanceViewModelFactory(repository)

        setContent {
            MyFinanceTheme {
                // Obtén el ViewModel usando el extension de compose
                val viewModel = androidx.lifecycle.viewmodel.compose.viewModel(
                    factory = viewModelFactory
                ) as FinanceViewModel
                MyFinanceApp(viewModel = viewModel)
            }
        }
    }
}
