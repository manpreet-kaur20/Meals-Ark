package com.example.aimealplanners.presentation.viewmodel

import com.example.aimealplanners.domain.model.Product

sealed class ProductUiState {
    data object Loading : ProductUiState()
    data class Success(val products: List<Product>) : ProductUiState()
    data class Error(val message: String) : ProductUiState()
}
