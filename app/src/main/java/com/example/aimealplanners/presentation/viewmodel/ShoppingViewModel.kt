package com.example.aimealplanners.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.aimealplanners.domain.model.ShoppingItem
import com.example.aimealplanners.domain.usecase.GetShoppingListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class ShoppingUiState {
    data object Loading : ShoppingUiState()
    data class Success(val items: List<ShoppingItem>) : ShoppingUiState()
    data class Error(val message: String) : ShoppingUiState()
}

@HiltViewModel
class ShoppingViewModel @Inject constructor(
    private val getShoppingListUseCase: GetShoppingListUseCase
) : BaseViewModel() {

    private val _uiState = MutableStateFlow<ShoppingUiState>(ShoppingUiState.Loading)
    val uiState: StateFlow<ShoppingUiState> = _uiState

    init {
        getShoppingList()
    }

    fun getShoppingList() {
        viewModelScope.launch {
            _uiState.value = ShoppingUiState.Loading
            getShoppingListUseCase()
                .onSuccess {
                    _uiState.value = ShoppingUiState.Success(it)
                }
                .onFailure {
                    _uiState.value = ShoppingUiState.Error(handleError(it))
                }
        }
    }
}
