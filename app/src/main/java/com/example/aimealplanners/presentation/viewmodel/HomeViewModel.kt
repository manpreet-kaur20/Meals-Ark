package com.example.aimealplanners.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.aimealplanners.domain.model.MealPlan
import com.example.aimealplanners.domain.usecase.GetDailyMealPlanUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

sealed class HomeUiState {
    data object Loading : HomeUiState()
    data class Success(val mealPlan: MealPlan) : HomeUiState()
    data class Error(val message: String) : HomeUiState()
}

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getDailyMealPlanUseCase: GetDailyMealPlanUseCase
) : BaseViewModel() {

    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val uiState: StateFlow<HomeUiState> = _uiState

    fun getDailyMealPlan(date: LocalDate) {
        viewModelScope.launch {
            _uiState.value = HomeUiState.Loading
            getDailyMealPlanUseCase(date.toString())
                .onSuccess {
                    _uiState.value = HomeUiState.Success(it)
                }
                .onFailure {
                    _uiState.value = HomeUiState.Error(handleError(it))
                }
        }
    }
}
