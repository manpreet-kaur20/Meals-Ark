package com.example.aimealplanners.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.aimealplanners.data.remote.dto.PurchaseSubscriptionRequest
import com.example.aimealplanners.data.remote.dto.SubscriptionStatusResponse
import com.example.aimealplanners.domain.usecase.GetProductsUseCase
import com.example.aimealplanners.domain.usecase.GetSubscriptionStatusUseCase
import com.example.aimealplanners.domain.usecase.PurchaseSubscriptionUseCase
import com.example.aimealplanners.domain.model.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class SubscriptionUiState {
    data object Idle : SubscriptionUiState()
    data object Loading : SubscriptionUiState()
    data class Success(val message: String = "") : SubscriptionUiState()
    data class Error(val message: String) : SubscriptionUiState()
}

@HiltViewModel
class SubscriptionViewModel @Inject constructor(
    private val getProductsUseCase: GetProductsUseCase,

    private val purchaseSubscriptionUseCase: PurchaseSubscriptionUseCase,

    private val getSubscriptionStatusUseCase: GetSubscriptionStatusUseCase
) : BaseViewModel() {

    private val _uiState = MutableStateFlow<SubscriptionUiState>(SubscriptionUiState.Idle)
    val uiState: StateFlow<SubscriptionUiState> = _uiState

    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>> = _products

    private val _subscriptionStatus = MutableStateFlow<SubscriptionStatusResponse?>(null)
    val subscriptionStatus: StateFlow<SubscriptionStatusResponse?> = _subscriptionStatus

    fun loadProducts() {
        viewModelScope.launch {
            _uiState.value = SubscriptionUiState.Loading
            getProductsUseCase()
                .onSuccess { productList ->
                    _products.value = productList
                    _uiState.value = SubscriptionUiState.Success()
                }
                .onFailure {
                    _uiState.value = SubscriptionUiState.Error(handleError(it))
                }
        }
    }

    fun loadSubscriptionStatus() {
        viewModelScope.launch {
            getSubscriptionStatusUseCase()
                .onSuccess { status ->
                    _subscriptionStatus.value = status
                }
                .onFailure {
                    // Silently handle - defaults to free plan display
                }
        }
    }

    fun purchaseSubscription(planId: String, purchaseToken: String? = null) {
        viewModelScope.launch {
            _uiState.value = SubscriptionUiState.Loading
            purchaseSubscriptionUseCase(PurchaseSubscriptionRequest(planId, purchaseToken))
                .onSuccess {
                    _uiState.value = SubscriptionUiState.Success("Subscription activated!")
                    loadSubscriptionStatus()
                }
                .onFailure {
                    _uiState.value = SubscriptionUiState.Error(handleError(it))
                }
        }
    }

    fun resetState() {
        _uiState.value = SubscriptionUiState.Idle
    }
}
