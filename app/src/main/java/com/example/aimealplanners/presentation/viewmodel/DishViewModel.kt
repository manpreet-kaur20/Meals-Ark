package com.example.aimealplanners.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.aimealplanners.domain.model.Dish
import com.example.aimealplanners.domain.usecase.GetDishesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class DishUiState {
    data object Loading : DishUiState()
    data class Success(val dishes: List<Dish>) : DishUiState()
    data class Error(val message: String) : DishUiState()
}

@HiltViewModel
class DishViewModel @Inject constructor(
    private val getDishesUseCase: GetDishesUseCase
) : BaseViewModel() {

    private val _uiState = MutableStateFlow<DishUiState>(DishUiState.Loading)
    val uiState: StateFlow<DishUiState> = _uiState

    init {
        getDishes()
    }

    fun getDishes() {
        viewModelScope.launch {
            _uiState.value = DishUiState.Loading
            try {
                getDishesUseCase().collect { dishes ->
                    _uiState.value = DishUiState.Success(dishes)
                }
            } catch (e: Exception) {
                _uiState.value = DishUiState.Error(handleError(e))
            }
        }
    }
}
