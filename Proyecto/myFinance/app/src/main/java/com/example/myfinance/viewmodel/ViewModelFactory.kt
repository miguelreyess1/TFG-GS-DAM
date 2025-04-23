package com.example.myfinance.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myfinance.data.repository.TransaccionRepository

class BalanceViewModelFactory(
    private val repository: TransaccionRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return BalanceViewModel(repository) as T
    }
}